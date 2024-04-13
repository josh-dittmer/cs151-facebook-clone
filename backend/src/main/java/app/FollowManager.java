package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FollowManager {
    private static final Logger log = LoggerFactory.getLogger(FollowManager.class);

    private Application app;

    public FollowManager(Application app) {
        this.app = app;
    }

    public boolean followUser(String srcUserId, String destUserId) {
        try {
            if (checkFollowing(srcUserId, destUserId)) {
                // user already followed
                return false;
            }

            Map<String, String> followData = new HashMap<String, String>();
            followData.put("SRC_USER_ID", srcUserId);
            followData.put("DEST_USER_ID", destUserId);
            this.app.getDatabaseConn().insert("FOLLOWS", followData);

            Map<String, String> userSrcUpdates = new HashMap<String, String>();
            userSrcUpdates.put("NUM_FOLLOWING", "NUM_FOLLOWING+1");
            this.app.getDatabaseConn().update("USERS", "USER_ID", srcUserId, userSrcUpdates);

            Map<String, String> userDestUpdates = new HashMap<String, String>();
            userDestUpdates.put("NUM_FOLLOWERS", "NUM_FOLLOWERS+1");
            this.app.getDatabaseConn().update("USERS", "USER_ID", destUserId, userDestUpdates);

        } catch(SQLException e) {
            log.error("SQL error while following user: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean unfollowUser(String srcUserId, String destUserId) {
        try {
            if (!checkFollowing(srcUserId, destUserId)) {
                // user not followed
                return false;
            }

            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("SRC_USER_ID", srcUserId);
            criteria.put("DEST_USER_ID", destUserId);
            this.app.getDatabaseConn().delete("FOLLOWS", criteria);

            Map<String, String> userSrcUpdates = new HashMap<String, String>();
            userSrcUpdates.put("NUM_FOLLOWING", "NUM_FOLLOWING-1");
            this.app.getDatabaseConn().update("USERS", "USER_ID", srcUserId, userSrcUpdates);

            Map<String, String> userDestUpdates = new HashMap<String, String>();
            userDestUpdates.put("NUM_FOLLOWERS", "NUM_FOLLOWERS-1");
            this.app.getDatabaseConn().update("USERS", "USER_ID", destUserId, userDestUpdates);

        } catch(SQLException e) {
            log.error("SQL error while unfollowing user: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean checkFollowing(String srcUserId, String destUserId) {
        try {
            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("SRC_USER_ID", srcUserId);
            criteria.put("DEST_USER_ID", destUserId);

            ResultSet resultSet = this.app.getDatabaseConn().lookup("FOLLOWS", "1", criteria);
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch(SQLException e) {
            log.error("SQL error while checking if user is followed: " + e.getMessage());
            return false;
        }

        return true;
    }

    public ArrayList<User> getUserFollowing(String userId, String myUserId) {
        ArrayList<User> users = new ArrayList<>();

        if (userId.equals("me")) {
            userId = myUserId;
        }

        try {
            ResultSet results = this.app.getDatabaseConn().lookup("FOLLOWS", "SRC_USER_ID", userId);
            ArrayList<String> userIds = new ArrayList<String>();

            while (results.next()) {
                //users.add(this.app.getUserManager().userFromResultSet(results, myUserId, true));
                userIds.add(results.getString("DEST_USER_ID"));
            }

            users = this.app.getUserManager().getUsers(userIds, myUserId);
        } catch(SQLException e) {
            log.error("SQL error while getting user following: " + e.getMessage());
            return null;
        }

        return users;
    }
}

package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RandomUID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class UserManager {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private Application app;

    public UserManager(Application app) {
        this.app = app;
    }

    public User createUser(String username, String password, String displayName, String bio) {
        String userId = RandomUID.generate(64);

        try {
            Map<String, String> postData = new HashMap<String, String>();
            postData.put("USER_ID", userId);
            postData.put("USERNAME", username);
            postData.put("PASSWORD", password);
            postData.put("DISPLAY_NAME", displayName);
            postData.put("BIO", bio);

            this.app.getDatabaseConn().insert("USERS", postData);
        } catch(SQLException e) {
            log.error("SQL error while creating user: " + e.getMessage());
            return null;
        }

        return new User(userId, username, password, bio, 0, 0, true);
    }

    public User getUser(String userId, String myUserId) {
        ArrayList<String> search = new ArrayList<String>();
        search.add(userId);

        ArrayList<User> result = getUsers(search, myUserId);
        if (result == null || result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public ArrayList<User> getUsers(ArrayList<String> userIds, String myUserId) {
        ArrayList<User> users = new ArrayList<>();

        if (userIds.isEmpty()) {
            return users;
        }

        for (int i = 0; i < userIds.size(); i++) {
            if (userIds.get(i).equals("me")) {
                userIds.set(i, myUserId);
            }
        }

        User user;

        try {
            ResultSet resultSet = this.app.getDatabaseConn().lookupAll("USERS", "USER_ID", userIds.stream().collect(Collectors.toSet()));

            while(resultSet.next()) {
                users.add(userFromResultSet(resultSet, myUserId));
            }
        } catch(SQLException e) {
            log.error("SQL error while getting user: " + e.getMessage());
            return null;
        }

        return users;
    }

    public User userFromResultSet(ResultSet results, String myUserId) throws SQLException {
        String userId = results.getString("USER_ID");
        String userUsername = results.getString("USERNAME");
        String userDisplayName = results.getString("DISPLAY_NAME");
        String userBio = results.getString("BIO");
        int userNumFollowers = results.getInt("NUM_FOLLOWERS");
        int userNumFollowing = results.getInt("NUM_FOLLOWING");
        boolean isMyProfile = (userId.equals(myUserId));

        return new User(userId, userUsername, userDisplayName, userBio, userNumFollowing, userNumFollowers, isMyProfile);
    }

    public User deleteUser(String userId) {
        return null;
    }
}

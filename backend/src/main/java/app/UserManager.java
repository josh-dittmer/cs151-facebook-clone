package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class UserManager {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private Application app;

    public UserManager(Application app) {
        this.app = app;
    }

    public User createUser() {
        return null;
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
                String userId = resultSet.getString("USER_ID");
                String userUsername = resultSet.getString("USERNAME");
                String userDisplayName = resultSet.getString("DISPLAY_NAME");
                String userBio = resultSet.getString("BIO");
                int userNumFollowers = resultSet.getInt("NUM_FOLLOWERS");
                int userNumFollowing = resultSet.getInt("NUM_FOLLOWING");
                boolean isMyProfile = (userId.equals(myUserId));

                user = new User(userId, userUsername, userDisplayName, userBio, userNumFollowing, userNumFollowers, isMyProfile);
                users.add(user);
            }
        } catch(SQLException e) {
            log.error("SQL error while getting user: " + e.getMessage());
            return null;
        }

        return users;
    }

    public User deleteUser(String userId) {
        return null;
    }
}

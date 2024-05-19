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

    //Constructor
    public UserManager(Application app) {
        this.app = app;
    }

    /*
     * Creates a user based on the specified parameters
     *
     * Params: username   : a String containing the username
     *         password   : a String containing the password
     *         displayName: a String containing the displayName (different from username)
     *         bio        : a String containing the user's bio / information
     *
     * Returns: the created User Object
     */
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

        return new User(userId, username, password, bio, 0, 0, true, false);
    }

    /*
     * Gets a User from the provided username
     *
     * Params: username: a String containing the username
     *         myUserId: a String containing current user's ID
     *
     * Returns: a User object that is being looked for
     */
    public User getUserByUsername(String username, String myUserId) {
        User user;

        try {
            ResultSet resultSet = this.app.getDatabaseConn().lookup("USERS", "USERNAME", username);

            // no results
            if (!resultSet.next()) {
                return null;
            }

            user = userFromResultSet(resultSet, myUserId);
        } catch(SQLException e) {
            log.error("SQL error while validating session: " + e.getMessage());
            return null;
        }

        return user;
    }

    //  should be changed to do not use getUsers
    public User getUser(String userId, String myUserId) {
        ArrayList<String> search = new ArrayList<String>();
        search.add(userId);

        ArrayList<User> result = getUsers(search, myUserId);
        if (result == null || result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    /*
     * Gets an ArrayList containing User objects
     *
     * Params: userIDs: an ArrayList containing a list of Strings which are the userIds
     *         myUserId: a String containing current user's ID
     *
     * Returns an ArrayList containing User objects
     */
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

    /*
     * Gets a User from a ResultSet which was retrieved from the Database
     *
     * Params: results    : the ResultSet which was obtainined from the Database
     *         myUserId   : current user's userID
     *         isFollowing: boolean value stating whether or not user is following these other users
     *
     * Returns: a User from the database specified by the params
     *
     */
    public User userFromResultSet(ResultSet results, String myUserId, boolean isFollowing) throws SQLException {
        String userId = results.getString("USER_ID");
        String userUsername = results.getString("USERNAME");
        String userDisplayName = results.getString("DISPLAY_NAME");
        String userBio = results.getString("BIO");
        int userNumFollowers = results.getInt("NUM_FOLLOWERS");
        int userNumFollowing = results.getInt("NUM_FOLLOWING");
        boolean isMyProfile = (userId.equals(myUserId));
        //boolean isFollowing = this.app.getFollowManager().checkFollowing(myUserId, userId);

        return new User(userId, userUsername, userDisplayName, userBio, userNumFollowers, userNumFollowing, isMyProfile, isFollowing);
    }

    /*
     * Goes through the database and finds a specified user
     *
     * Params: results : a ResultSet from the database
     *         myUserId: String containing current user's userID
     *
     * Returns: A user specified by the userID
     */
    public User userFromResultSet(ResultSet results, String myUserId) throws SQLException {
        User user = userFromResultSet(results, myUserId, false);
        user.setIsFollowing(this.app.getFollowManager().checkFollowing(myUserId, user.getUserId()));

        return user;
    }

    public User deleteUser(String userId) {
        return null;
    }
}

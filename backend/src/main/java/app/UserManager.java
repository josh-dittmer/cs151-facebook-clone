package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserManager {
    private static final Logger log = LoggerFactory.getLogger(UserManager.class);

    private DatabaseConnection databaseConn;

    public UserManager(DatabaseConnection databaseConn) {
        this.databaseConn = databaseConn;
    }

    public User createUser() {
        return null;
    }

    public User getUser(String userId) {
        User user;

        try {
            ResultSet resultSet = this.databaseConn.lookup("USERS", "USER_ID", userId);

            // no results
            if (!resultSet.next()) {
                return null;
            }

            String userUsername = resultSet.getString("USERNAME");
            String userDisplayName = resultSet.getString("DISPLAY_NAME");
            String userBio = resultSet.getString("BIO");

            user = new User(userId, userUsername, userDisplayName, userBio);

        } catch(SQLException e) {
            log.error("SQL error while getting user: " + e.getMessage());
            return null;
        }

        return user;
    }

    public User deleteUser(String userId) {
        return null;
    }
}

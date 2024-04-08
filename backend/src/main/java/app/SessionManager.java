package app;

import controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RandomUID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SessionManager {
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);

    private DatabaseConnection databaseConn;

    public SessionManager(DatabaseConnection databaseConn) {
        this.databaseConn = databaseConn;
    }

    public Session createSession(String username, String password) {
        Session session;

        try {
            ResultSet resultSet = this.databaseConn.lookup("USERS", "USERNAME", username);

            // no results
            if (!resultSet.next()) {
                return null;
            }

            String userId = resultSet.getString("USER_ID");
            String userPassword = resultSet.getString("PASSWORD");

            if (!password.equals(userPassword)) {
                return null;
            }

            // generate random session token
            String token = RandomUID.generate(64);

            this.databaseConn.insert("SESSIONS", "TOKEN, USER_ID", "'" + token + "', '" + userId + "'");
            session = new Session(token, userId);

            log.info("Created session [" + token + "]");

        } catch(SQLException e) {
            log.error("SQL error while creating session: " + e.getMessage());
            return null;
        }

        return session;
    }

    public Session validateSession(String token) {
        Session session;

        try {
            ResultSet resultSet = this.databaseConn.lookup("SESSIONS", "TOKEN", token);

            // no results
            if (!resultSet.next()) {
                log.warn("Session [" + token + "] not found");
                return null;
            }

            // check if session is expired, otherwise delete
            Timestamp timestamp = resultSet.getTimestamp("TIMESTAMP");
            System.out.println(timestamp);

            String sessionUserId = resultSet.getString("USER_ID");

            // update session timestamp
            this.databaseConn.update("SESSIONS", "TOKEN", token, "TIMESTAMP=CURRENT_TIMESTAMP()");

            session = new Session(token, sessionUserId);

        } catch(SQLException e) {
            log.error("SQL error while validating session: " + e.getMessage());
            return null;
        }

        log.info("Validated session [" + token + "]");

        return session;
    }

    public Session deleteSession(String token) {
        Session session;

        try {
            session = this.validateSession(token);
            if (session == null) {
                return null;
            }

            this.databaseConn.delete("SESSIONS", "TOKEN", token);
        } catch(SQLException e) {
            log.error("SQL error while deleting session: " + e.getMessage());
            return null;
        }

        log.info("Deleted session [" + token + "]");

        return session;
    }
}

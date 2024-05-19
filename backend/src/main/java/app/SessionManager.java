package app;

import controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RandomUID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class SessionManager {
    private static final Logger log = LoggerFactory.getLogger(SessionManager.class);

    private Application app;

    //Constructor
    public SessionManager(Application app) {
        this.app = app;
    }

    /*
     * creates a session for the specified user
     *
     * Params: username
     *         password
     *
     * Returns: Returns a Session object
     */
    public Session createSession(String username, String password) {
        Session session;

        try {
            ResultSet resultSet = this.app.getDatabaseConn().lookup("USERS", "USERNAME", username);

            // no results
            if (!resultSet.next()) {
                System.out.println("test");
                return null;
            }

            String userId = resultSet.getString("USER_ID");
            String userPassword = resultSet.getString("PASSWORD");

            if (!password.equals(userPassword)) {
                return null;
            }

            // generate random session token
            String token = RandomUID.generate(64);

            Map<String, String> sessionData = new HashMap<String, String>();
            sessionData.put("TOKEN", token);
            sessionData.put("USER_ID", userId);

            this.app.getDatabaseConn().insert("SESSIONS", sessionData);

            session = new Session(token, userId);

            log.info("Created session [" + token + "]");

        } catch(SQLException e) {
            log.error("SQL error while creating session: " + e.getMessage());
            return null;
        }

        return session;
    }

    /*
     * Validates the session based on a token and then returns it
     *
     * Params: token: a String containing the token
     *
     * Returns a Session object
     */
    public Session validateSession(String token) {
        Session session;

        try {
            ResultSet resultSet = this.app.getDatabaseConn().lookup("SESSIONS", "TOKEN", token);

            // no results
            if (!resultSet.next()) {
                log.warn("Session [" + token + "] not found");
                return null;
            }

            // check if session is expired, otherwise delete
            Timestamp timestamp = resultSet.getTimestamp("TIMESTAMP");
            //System.out.println(timestamp);

            String sessionUserId = resultSet.getString("USER_ID");

            // update session timestamp
            /*Map<String, String> sessionUpdates = new HashMap<String, String>();
            sessionUpdates.put("TIMESTAMP", "CURRENT_TIMESTAMP()");

            this.app.getDatabaseConn().update("SESSIONS", "TOKEN", token, sessionUpdates);*/
            this.app.getDatabaseConn().updateTimestamp("SESSIONS", "TOKEN", token);

            session = new Session(token, sessionUserId);

        } catch(SQLException e) {
            log.error("SQL error while validating session: " + e.getMessage());
            return null;
        }

        log.info("Validated session [" + token + "]");

        return session;
    }

    /*
     * Deletes a session specified by the token
     *
     * Params: token: String containing the token
     *
     * Returns the Session object
     */
    public Session deleteSession(String token) {
        Session session;

        try {
            session = this.validateSession(token);
            if (session == null) {
                return null;
            }

            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("TOKEN", token);
            this.app.getDatabaseConn().delete("SESSIONS", criteria);
        } catch(SQLException e) {
            log.error("SQL error while deleting session: " + e.getMessage());
            return null;
        }

        log.info("Deleted session [" + token + "]");

        return session;
    }
}

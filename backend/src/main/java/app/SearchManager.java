package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchManager {
    private static final Logger log = LoggerFactory.getLogger(SearchManager.class);

    private Application app;

    //Constructor
    public SearchManager(Application app) {
        this.app = app;
    }

    //Searches db for user
    public ArrayList<User> search(String query, String myUserId) {
        ArrayList<User> users = new ArrayList<User>();

        try {
            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("USERNAME", query);
            criteria.put("DISPLAY_NAME", query);

            ResultSet results = this.app.getDatabaseConn().contains("USERS", criteria);

            while(results.next()) {
                users.add(this.app.getUserManager().userFromResultSet(results, myUserId));
            }
        } catch(SQLException e) {
            log.error("SQL error while searching for user: " + e.getMessage());
            return null;
        }

        return users;
    }
}

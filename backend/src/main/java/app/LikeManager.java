package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LikeManager {
    private static final Logger log = LoggerFactory.getLogger(LikeManager.class);

    private DatabaseConnection databaseConn;

    public LikeManager(DatabaseConnection databaseConn) {
        this.databaseConn = databaseConn;
    }

    public boolean checkLiked(String userId, String postId) {
        try {
            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("USER_ID", userId);
            criteria.put("POST_ID", postId);

            ResultSet resultSet = this.databaseConn.lookup("LIKES", "1", criteria);
            if (!resultSet.next()) {
                return false;
            }
        } catch(SQLException e) {
            log.error("SQL error while getting user posts: " + e.getMessage());
            return false;
        }

        return true;
    }

}

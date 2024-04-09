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

    public boolean likePost(String userId, String postId) {
        try {
            if (checkLiked(userId, postId)) {
                // post already liked
                return false;
            }

            this.databaseConn.insert("LIKES", "POST_ID, USER_ID", "'" + postId + "', '" + userId + "'");
            this.databaseConn.update("POSTS", "POST_ID", postId, "NUM_LIKES=NUM_LIKES+1");
        } catch(SQLException e) {
            log.error("SQL error while liking post: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean unlikePost(String userId, String postId) {
        try {
            if (!checkLiked(userId, postId)) {
                // post not liked
                return false;
            }

            this.databaseConn.delete("LIKES", "POST_ID", postId);
            this.databaseConn.update("POSTS", "POST_ID", postId, "NUM_LIKES=NUM_LIKES-1");
        } catch(SQLException e) {
            log.error("SQL error while unliking post: " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean checkLiked(String userId, String postId) {
        try {
            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("USER_ID", userId);
            criteria.put("POST_ID", postId);

            ResultSet resultSet = this.databaseConn.lookup("LIKES", "1", criteria);
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch(SQLException e) {
            log.error("SQL error while checking if post is liked: " + e.getMessage());
            return false;
        }

        return true;
    }

}

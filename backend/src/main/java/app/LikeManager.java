package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class LikeManager {
    private static final Logger log = LoggerFactory.getLogger(LikeManager.class);

    private Application app;

    public LikeManager(Application app) {
        this.app = app;
    }

    public boolean likePost(String userId, String postId) {
        try {
            if (checkLiked(userId, postId)) {
                // post already liked
                return false;
            }

            Map<String, String> likeData = new HashMap<String, String>();
            likeData.put("POST_ID", postId);
            likeData.put("USER_ID", userId);
            this.app.getDatabaseConn().insert("LIKES", likeData);

            Map<String, String> likeUpdates = new HashMap<String, String>();
            likeUpdates.put("NUM_LIKES", "NUM_LIKES+1");
            this.app.getDatabaseConn().update("POSTS", "POST_ID", postId, likeUpdates);

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

            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("POST_ID", postId);
            criteria.put("USER_ID", userId);
            this.app.getDatabaseConn().delete("LIKES", criteria);

            Map<String, String> likeUpdates = new HashMap<String, String>();
            likeUpdates.put("NUM_LIKES", "NUM_LIKES-1");
            this.app.getDatabaseConn().update("POSTS", "POST_ID", postId, likeUpdates);

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

            ResultSet resultSet = this.app.getDatabaseConn().lookup("LIKES", "1", criteria);
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

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

    //Constructor
    public LikeManager(Application app) {
        this.app = app;
    }

    /*
     * Causes user with "userId" to likes the specified post with "postId"
     *
     * Params:  userId: the id of a user in String format
     *          postId: the id of a post in String format
     *
     * Returns: True if successfully liked the post
     *          False if not liked or exception occurred
     */
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

    /*
     * Causes user with "userId" to unlike the specified post with "postId"
     *
     * Params:  userId: the id of a user in String format
     *          postId: the id of a post in String format
     *
     * Returns: True if sucessfully unliked the post
     *          False if post was never liked in the first place or exception occurred
     */
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

    /*
     * Checks if user with "userId" liked a post specified by "postId"
     *
     * Params:  userId: the id of a user in String format
     *          postId: the id of a post in String format
     *
     * Returns: True if liked
     *          False if not liked or exception occurred
     */
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

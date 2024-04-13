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

public class PostManager {
    private static final Logger log = LoggerFactory.getLogger(PostManager.class);

    private Application app;

    public PostManager(Application app) {
        this.app = app;
    }

    public ArrayList<Post> getUserPosts(ArrayList<User> users, String myUserId) {
        ArrayList<Post> posts = new ArrayList<Post>();

        Map<String, User> userMap = new HashMap<String, User>();
        for (User user : users) {
            userMap.put(user.getUserId(), user);
        }

        try {
            ResultSet postResultSet = this.app.getDatabaseConn().lookupAll("POSTS", "USER_ID", userMap.keySet());

            while (postResultSet.next()) {
                String userId = postResultSet.getString("USER_ID");

                User user = userMap.get(userId);
                if (user == null) {
                    continue;
                }

                String postId = postResultSet.getString("POST_ID");
                String postText = postResultSet.getString("TEXT");
                boolean postHasImage = postResultSet.getBoolean("HAS_IMAGE");
                int postNumLikes = postResultSet.getInt("NUM_LIKES");
                int postNumComments = postResultSet.getInt("NUM_COMMENTS");
                Timestamp postTimestamp = postResultSet.getTimestamp("TIMESTAMP");
                boolean postLiked = this.app.getLikeManager().checkLiked(myUserId, postId);
                boolean isMyPost = userId.equals(myUserId);

                posts.add(new Post(postId, user.getUserId(), user.getUsername(), user.getDisplayName(), postText, postHasImage, postLiked, postNumLikes, postNumComments, postTimestamp, isMyPost));
            }
        } catch(SQLException e) {
            log.error("SQL error while getting user posts: " + e.getMessage());
            return null;
        }

        return posts;
    }

    public String createPost(String userId, String text, boolean hasImage) {
        String postId = RandomUID.generate(64);

        try {
            Map<String, String> postData = new HashMap<String, String>();
            postData.put("POST_ID", postId);
            postData.put("USER_ID", userId);
            postData.put("TEXT", text);
            postData.put("HAS_IMAGE", (hasImage) ? "TRUE" : "FALSE");

            this.app.getDatabaseConn().insert("POSTS", postData);
        } catch(SQLException e) {
            log.error("SQL error while creating user post: " + e.getMessage());
            return null;
        }

        return postId;
    }

    public boolean deletePost(String userId, String postId) {
        try {
            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("POST_ID", postId);
            criteria.put("USER_ID", userId);

            this.app.getDatabaseConn().delete("POSTS", criteria);
        } catch(SQLException e) {
            log.error("SQL error while deleting user post: " + e.getMessage());
            return false;
        }

        return true;
    }
}

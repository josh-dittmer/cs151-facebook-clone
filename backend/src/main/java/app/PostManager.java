package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RandomUID;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class PostManager {
    private static final Logger log = LoggerFactory.getLogger(PostManager.class);

    private DatabaseConnection databaseConn;

    public PostManager(DatabaseConnection databaseConn) {
        this.databaseConn = databaseConn;
    }

    public ArrayList<Post> getUserPosts(User user) {
        ArrayList<Post> posts = new ArrayList<Post>();

        try {
            ResultSet postResultSet = this.databaseConn.lookup("POSTS", "USER_ID", user.getUserId());

            while (postResultSet.next()) {
                String postId = postResultSet.getString("POST_ID");
                String postText = postResultSet.getString("TEXT");
                boolean postHasImage = postResultSet.getBoolean("HAS_IMAGE");
                int postNumLikes = postResultSet.getInt("NUM_LIKES");
                int postNumComments = postResultSet.getInt("NUM_COMMENTS");
                Timestamp postTimestamp = postResultSet.getTimestamp("TIMESTAMP");

                posts.add(new Post(postId, user.getUserId(), user.getUsername(), user.getDisplayName(), postText, postHasImage, false, postNumLikes, postNumComments, postTimestamp));
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
            String columns = "POST_ID, USER_ID, TEXT, HAS_IMAGE";
            String values = "'" + postId + "', " +
                    "'" + userId + "', " +
                    "'" + text + "', " +
                    "'" + ((hasImage) ? "TRUE" : "FALSE") + "'";

            this.databaseConn.insert("POSTS", columns, values);
        } catch(SQLException e) {
            log.error("SQL error while creating user post: " + e.getMessage());
            return null;
        }

        return postId;
    }
}

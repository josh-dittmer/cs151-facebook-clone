package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    public ArrayList<Post> getUserPosts(UserManager userManager, String userId) {
        ArrayList<Post> posts = new ArrayList<Post>();

        try {
            User user = userManager.getUser(userId);
            if (user == null) {
                return null;
            }

            ResultSet resultSet = this.databaseConn.lookup("POSTS", "USER_ID", userId);

            while (resultSet.next()) {
                String postId = resultSet.getString("POST_ID");
                String postText = resultSet.getString("TEXT");
                boolean postHasImage = resultSet.getBoolean("HAS_IMAGE");
                int postNumLikes = resultSet.getInt("NUM_LIKES");
                int postNumComments = resultSet.getInt("NUM_COMMENTS");
                Timestamp postTimestamp = resultSet.getTimestamp("TIMESTAMP");

                // lookup if post is liked

                posts.add(new Post(postId, userId, user.getUsername(), user.getDisplayName(), postText, postHasImage, false, postNumLikes, postNumComments, postTimestamp));
            }
        } catch(SQLException e) {
            log.error("SQL error while getting user: " + e.getMessage());
            return null;
        }

        return posts;
    }
}

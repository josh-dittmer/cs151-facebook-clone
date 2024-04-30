package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RandomUID;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommentManager {
    private static final Logger log = LoggerFactory.getLogger(CommentManager.class);

    private Application app;

    public CommentManager(Application app) {
        this.app = app;
    }

    public String createComment(String postId, String userId, String text) {
        String commentId = RandomUID.generate(64);

        try {
            Map<String, String> commentData = new HashMap<String, String>();
            commentData.put("COMMENT_ID", commentId);
            commentData.put("POST_ID", postId);
            commentData.put("USER_ID", userId);
            commentData.put("TEXT", text);

            this.app.getDatabaseConn().insert("COMMENTS", commentData);
        } catch(SQLException e) {
            log.error("SQL error while creating comment: " + e.getMessage());
            return null;
        }

        return commentId;
    }

    public ArrayList<Comment> getComments(String postId, String myUserId) {
        ArrayList<Comment> comments = new ArrayList<Comment>();
        Map<String, User> userMap = new HashMap<String, User>();

        try {
            ResultSet resultSet = this.app.getDatabaseConn().lookup("COMMENTS", "POST_ID", postId);

            while(resultSet.next()) {
                String commentId = resultSet.getString("COMMENT_ID");
                String commentUserId = resultSet.getString("USER_ID");
                String commentText = resultSet.getString("TEXT");
                Timestamp commentTimestamp = resultSet.getTimestamp("TIMESTAMP");

                User user = userMap.get(commentUserId);
                if (user == null) {
                    user = this.app.getUserManager().getUser(commentUserId, myUserId);
                    if (user == null) {
                        log.warn("User [" + commentUserId + "] not found");
                        continue;
                    }

                    userMap.put(commentUserId, user);
                }

                comments.add(new Comment(commentId, postId, commentUserId, user.getUsername(), user.getDisplayName(), commentText, user.isMyProfile(), commentTimestamp));
            }
        } catch(SQLException e) {
            log.error("SQL error while validating session: " + e.getMessage());
            return null;
        }

        return comments;
    }

    public boolean deleteComment(String userId, String commentId) {
        User user;

        try {
            user = this.app.getUserManager().getUser(userId, userId);
            if (user == null) {
                return false;
            }

            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("COMMENT_ID", commentId);
            criteria.put("USER_ID", userId);

            this.app.getDatabaseConn().delete("COMMENTS", criteria);
        } catch(SQLException e) {
            log.error("SQL error while deleting user comment: " + e.getMessage());
            return false;
        }

        return true;
    }
}

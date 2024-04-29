package app;

import java.sql.Timestamp;

public class Comment {
    private String commentId;
    private String postId;
    private String userId;
    private String username;
    private String displayName;
    private String text;
    private boolean isMyComment;
    private Timestamp timestamp;

    public Comment(String commentId, String postId, String userId, String username, String displayName, String text, boolean isMyComment, Timestamp timestamp) {
        this.commentId = commentId;
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.text = text;
        this.isMyComment = isMyComment;
        this.timestamp = timestamp;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isMyComment() {
        return isMyComment;
    }

    public void setMyComment(boolean myComment) {
        isMyComment = myComment;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

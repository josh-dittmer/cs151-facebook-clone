package app;

import java.sql.Timestamp;

public class Post {
    private String postId;
    private String userId;
    private String username;
    private String displayName;
    private String text;
    private boolean hasImage;
    private boolean liked;
    private int numLikes;
    private int numComments;
    private Timestamp timestamp;

    public Post(String postId, String userId, String username, String displayName, String text, boolean hasImage, boolean liked, int numLikes, int numComments, Timestamp timestamp) {
        this.postId = postId;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.text = text;
        this.hasImage = hasImage;
        this.liked = liked;
        this.numLikes = numLikes;
        this.numComments = numComments;
        this.timestamp = timestamp;
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

    public boolean hasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public boolean isLiked() {
        return this.liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

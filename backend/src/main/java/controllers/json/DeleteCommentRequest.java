package controllers.json;

public class DeleteCommentRequest {
    private String postId;
    private String commentId;
    private String token;

    public DeleteCommentRequest(String postId, String commentId, String token) {
        this.postId = postId;
        this.commentId = commentId;
        this.token = token;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

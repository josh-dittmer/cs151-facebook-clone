package controllers.json;

public class CreateCommentResponse {
    private String commentId;

    public CreateCommentResponse(String commentId) {
        this.commentId = commentId;
    }

    public void setCommentId(String postId) {
        this.commentId = commentId;
    }

    public String getCommentId() {
        return this.commentId;
    }

    @Override
    public String toString() {
        return "{\"success\":true,\"commentId\":\"" + commentId + "\"}";
    }
}

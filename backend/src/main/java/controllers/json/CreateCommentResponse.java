package controllers.json;

public class CreateCommentResponse {
    private String commentId;
    private String userId;
    private String username;
    private String displayName;

    public CreateCommentResponse(String commentId, String userId, String username, String displayName) {
        this.commentId = commentId;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
    }

    public void setCommentId(String postId) {
        this.commentId = commentId;
    }

    public String getCommentId() {
        return this.commentId;
    }

    @Override
    public String toString() {
        String str = "{\"success\":true,";
        str += "\"commentId\":\"" + commentId + "\",";
        str += "\"userId\":\"" + userId + "\",";
        str += "\"username\":\"" + username + "\",";
        str += "\"displayName\":\"" + displayName + "\"}";

        return str;
    }
}

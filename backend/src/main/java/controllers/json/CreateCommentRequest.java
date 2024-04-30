package controllers.json;

public class CreateCommentRequest {
    private String postId;
    private String text;

    private String token;

    public CreateCommentRequest(String postId, String text, String token) {
        this.postId = postId;
        this.text = text;
        this.token = token;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

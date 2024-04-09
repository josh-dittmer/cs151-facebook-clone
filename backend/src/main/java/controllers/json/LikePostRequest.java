package controllers.json;

public class LikePostRequest {
    private String postId;

    private String token;

    public LikePostRequest() {
        this.postId = "";
        this.token = "";
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

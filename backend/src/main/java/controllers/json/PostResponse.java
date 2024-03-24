package controllers.json;

public class PostResponse {
    private String postId;

    public PostResponse(String postId) {
        this.postId = postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostId() {
        return this.postId;
    }

    @Override
    public String toString() {
        return "{\"success\":true,\"postId\":\"" + postId + "\"}";
    }
}

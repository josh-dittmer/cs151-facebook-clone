package controllers.json;

public class CreatePostResponse {
    private String postId;

    public CreatePostResponse(String postId) {
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

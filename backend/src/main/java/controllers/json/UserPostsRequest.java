package controllers.json;

public class UserPostsRequest {
    private String userId;
    private int page;
    private String token;

    public UserPostsRequest() {
        this.userId = "";
        this.page = 0;
        this.token = "";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getToken() {
        return userId;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

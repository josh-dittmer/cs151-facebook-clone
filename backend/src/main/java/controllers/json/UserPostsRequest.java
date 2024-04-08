package controllers.json;

public class UserPostsRequest {
    private String[] userIds;
    private int page;
    private String token;

    public UserPostsRequest() {
        this.userIds = new String[0];
        this.page = 0;
        this.token = "";
    }

    public String[] getUserIds() {
        return userIds;
    }

    public void setUserIds(String[] userIds) {
        this.userIds = userIds;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

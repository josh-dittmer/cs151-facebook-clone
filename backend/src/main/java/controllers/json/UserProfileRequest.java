package controllers.json;

public class UserProfileRequest {
    private String userId;
    private String token;

    public UserProfileRequest() {
        this.userId = "";
        this.token = "";
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

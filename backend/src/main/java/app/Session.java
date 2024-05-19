package app;

public class Session {
    private String token;
    private String userId;

    //Constructor
    public Session(String token, String userId) {
        this.token = token;
        this.userId = userId;
    }

    //Getters and Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

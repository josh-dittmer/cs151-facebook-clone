package controllers.json;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest() {
        this.username = "";
        this.password = "";
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }
}

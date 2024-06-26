package controllers.json;

public class SignupRequest {
    private String username;
    private String password;
    private String displayName;
    private String bio;

    public SignupRequest() {
        this.username = "";
        this.password = "";
        this.displayName = "";
        this.bio = "";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}

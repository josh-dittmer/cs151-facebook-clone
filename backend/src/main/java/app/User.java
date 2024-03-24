package app;
public class User {
    private String userId;
    private String username;
    private String displayName;
    private String bio;

    public User(String userId, String username, String displayName, String bio) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.bio = bio;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

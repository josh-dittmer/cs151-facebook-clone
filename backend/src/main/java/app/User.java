package app;
public class User {
    private String userId;
    private String username;
    private String displayName;
    private String bio;
    private int numFollowers;
    private int numFollowing;
    private boolean isMyProfile;

    public User(String userId, String username, String displayName, String bio, int numFollowers, int numFollowing, boolean isMyProfile) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.bio = bio;
        this.numFollowers = numFollowers;
        this.numFollowing = numFollowing;
        this.isMyProfile = isMyProfile;
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
    public int getNumFollowers() {
        return this.numFollowers;
    }

    public void setNumFollowers(int numFollowers) {
        this.numFollowers = numFollowers;
    }

    public int getNumFollowing() {
        return this.numFollowing;
    }

    public void setNumFollowing(int numFollowing) {
        this.numFollowing = numFollowing;
    }

    public boolean isMyProfile() {
        return this.isMyProfile;
    }

    public void setIsMyProfile(boolean isMyProfile) {
        this.isMyProfile = isMyProfile;
    }
}

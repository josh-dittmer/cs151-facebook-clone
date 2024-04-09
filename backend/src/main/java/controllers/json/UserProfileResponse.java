package controllers.json;

import app.User;

public class UserProfileResponse {
    private User user;
    public UserProfileResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        String str = "{\"success\":true,";
        str += "\"username\":\"" + this.user.getUsername() + "\",";
        str += "\"displayName\":\"" + this.user.getDisplayName() + "\",";
        str += "\"bio\":\"" + this.user.getBio() + "\",";
        str += "\"numFollowers\":" + this.user.getNumFollowers() + ",";
        str += "\"numFollowing\":" + this.user.getNumFollowing() + ",";
        str += "\"isMyProfile\":" + this.user.isMyProfile() + "}";

        return str;
    }
}

package controllers.json;

import app.User;
import util.Format;

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
        String str = "{\"success\":true,\"user\":{";
        str += "\"userId\":\"" + this.user.getUserId() + "\",";
        str += "\"username\":\"" + Format.jsonEscape(this.user.getUsername()) + "\",";
        str += "\"displayName\":\"" + Format.jsonEscape(this.user.getDisplayName()) + "\",";
        str += "\"bio\":\"" + Format.jsonEscape(this.user.getBio()) + "\",";
        str += "\"numFollowers\":" + this.user.getNumFollowers() + ",";
        str += "\"numFollowing\":" + this.user.getNumFollowing() + ",";
        str += "\"isMyProfile\":" + this.user.isMyProfile() + ",";
        str += "\"isFollowing\":" + user.isFollowing() + "}}";

        return str;
    }
}

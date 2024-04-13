package controllers.json.generic;

import app.Post;
import app.User;

import java.util.ArrayList;

public class UserListResponse {
    private ArrayList<User> users;

    public UserListResponse(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        String str = "{\"success\":true,\"users\":[";

        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            str += "{\"userId\":\"" + user.getUserId() + "\",";
            str += "\"username\":\"" + user.getUsername() + "\",";
            str += "\"displayName\":\"" + user.getDisplayName() + "\",";
            str += "\"bio\":\"" + user.getBio() + "\",";
            str += "\"numFollowers\":" + user.getNumFollowers() + ",";
            str += "\"numFollowing\":" + user.getNumFollowing() + ",";
            str += "\"isMyProfile\":" + user.isMyProfile() + ",";
            str += "\"isFollowing\":" + user.isFollowing() + "}";

            if (i != users.size() - 1) {
                str += ", ";
            }
        }

        str += "]}";

        return str;
    }
}

package controllers.json;

import app.User;

import java.util.ArrayList;

public class SearchResponse {
    private ArrayList<User> users;

    public SearchResponse(ArrayList<User> users) {
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
        return "";
    }
}

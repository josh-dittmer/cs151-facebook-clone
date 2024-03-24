package controllers.json;

import app.Post;

import java.util.ArrayList;

public class PostListResponse {
    private ArrayList<Post> posts;

    public PostListResponse(ArrayList<Post> posts) {
        this.posts = posts;
    }

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    @Override
    public String toString() {
        return "";
    }

}

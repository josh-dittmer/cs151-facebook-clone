package controllers.json;

import app.Post;

import java.util.ArrayList;

public class UserPostsResponse {
    private ArrayList<Post> posts;

    public UserPostsResponse(ArrayList<Post> posts) {
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
        String str = "{\"success\":true,\"posts\":[";

        for (int i = 0; i < posts.size(); i++) {
            Post post = posts.get(i);

            str += "{\"postId\":\"" + post.getPostId() + "\",";
            str += "\"userId\":\"" + post.getUserId() + "\",";
            str += "\"username\":\"" + post.getUsername() + "\",";
            str += "\"displayName\":\"" + post.getDisplayName() + "\",";
            str += "\"text\":\"" + post.getText() + "\",";
            str += "\"hasImage\":\"" + post.hasImage() + "\",";
            str += "\"liked\":" + post.isLiked() + ",";
            str += "\"numLikes\":\"" + post.getNumLikes() + "\",";
            str += "\"numComments\":\"" + post.getNumComments() + "\",";
            str += "\"timestamp\":\"" + post.getTimestamp() + "\"}";

            if (i != posts.size() - 1) {
                str += ", ";
            }
        }

        str += "]}";

        return str;
    }

}

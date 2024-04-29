package controllers.json; //comment

import app.Post;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

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
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.US);
            String formattedTimestamp = sdf.format(post.getTimestamp());


            str += "{\"postId\":\"" + post.getPostId() + "\",";
            str += "\"userId\":\"" + post.getUserId() + "\",";
            str += "\"username\":\"" + post.getUsername() + "\",";
            str += "\"displayName\":\"" + post.getDisplayName() + "\",";
            str += "\"text\":\"" + post.getText() + "\",";
            str += "\"hasImage\":" + post.hasImage() + ",";
            str += "\"liked\":" + post.isLiked() + ",";
            str += "\"numLikes\":\"" + post.getNumLikes() + "\",";
            str += "\"numComments\":\"" + post.getNumComments() + "\",";
            str += "\"timestamp\":\"" + formattedTimestamp + "\",";
            str += "\"isMyPost\":" + post.isMyPost() + "}";

            if (i != posts.size() - 1) {
                str += ", ";
            }
        }

        str += "]}";

        return str;
    }

}

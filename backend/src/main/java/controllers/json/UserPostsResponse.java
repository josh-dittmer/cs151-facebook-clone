package controllers.json;

import app.Comment;
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
            str += "\"hasImage\":" + post.hasImage() + ",";
            str += "\"liked\":" + post.isLiked() + ",";
            str += "\"numLikes\":\"" + post.getNumLikes() + "\",";
            str += "\"numComments\":\"" + post.getNumComments() + "\",";
            str += "\"timestamp\":\"" + post.getTimestamp() + "\",";
            str += "\"isMyPost\":" + post.isMyPost() + ",";
            str += "\"comments\":[";

            for (int j = 0; j < post.getComments().size(); j++) {
                Comment comment = post.getComments().get(j);

                str += "{\"commentId\":\"" + comment.getCommentId() + "\",";
                str += "\"postId\":\"" + comment.getPostId() + "\",";
                str += "\"userId\":\"" + comment.getUserId() + "\",";
                str += "\"username\":\"" + comment.getUsername() + "\",";
                str += "\"displayName\":\"" + comment.getDisplayName() + "\",";
                str += "\"text\":\"" + comment.getText() + "\",";
                str += "\"isMyComment\":" + comment.isMyComment() + ",";
                str += "\"timestamp\":\"" + comment.getTimestamp() + "\"}";

                if (j != post.getComments().size() - 1) {
                    str += ", ";
                }
            }

            str += "]}";

            if (i != posts.size() - 1) {
                str += ", ";
            }
        }

        str += "]}";

        return str;
    }

}

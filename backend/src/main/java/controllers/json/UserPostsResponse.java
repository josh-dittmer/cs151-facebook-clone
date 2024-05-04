package controllers.json; //standard time

import app.Comment;
import app.Post;
import util.Format;

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

            String formattedPostTimestamp = sdf.format(post.getTimestamp());

            str += "{\"postId\":\"" + post.getPostId() + "\",";
            str += "\"userId\":\"" + post.getUserId() + "\",";
            str += "\"username\":\"" + Format.jsonEscape(post.getUsername()) + "\",";
            str += "\"displayName\":\"" + Format.jsonEscape(post.getDisplayName()) + "\",";
            str += "\"text\":\"" + Format.jsonEscape(post.getText()) + "\",";
            str += "\"hasImage\":" + post.hasImage() + ",";
            str += "\"liked\":" + post.isLiked() + ",";
            str += "\"numLikes\":\"" + post.getNumLikes() + "\",";
            str += "\"numComments\":\"" + post.getNumComments() + "\",";
            str += "\"timestamp\":\"" + formattedPostTimestamp + "\",";
            str += "\"isMyPost\":" + post.isMyPost() + ",";
            str += "\"comments\":[";

            for (int j = 0; j < post.getComments().size(); j++) {
                Comment comment = post.getComments().get(j);

                String formattedCommentTimestamp = sdf.format(comment.getTimestamp());

                str += "{\"commentId\":\"" + comment.getCommentId() + "\",";
                str += "\"postId\":\"" + comment.getPostId() + "\",";
                str += "\"userId\":\"" + comment.getUserId() + "\",";
                str += "\"username\":\"" + Format.jsonEscape(comment.getUsername()) + "\",";
                str += "\"displayName\":\"" + Format.jsonEscape(comment.getDisplayName()) + "\",";
                str += "\"text\":\"" + Format.jsonEscape(comment.getText()) + "\",";
                str += "\"isMyComment\":" + comment.isMyComment() + ",";
                str += "\"timestamp\":\"" + formattedCommentTimestamp + "\"}";

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

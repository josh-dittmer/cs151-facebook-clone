package controllers;

import app.*;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.*;
import controllers.json.generic.ErrorResponse;
import controllers.json.generic.ItemActionRequest;
import controllers.json.generic.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

@Path
public class ContentController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private Application app;

    public ContentController(Application app) {
        this.app = app;
    }

    @POST(value="/user_profile", responseType= ResponseType.JSON)
    public String viewUserProfile(@Body UserProfileRequest data) {
        if (data == null) {
            log.warn("/user_profile: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            return new ErrorResponse("session not found", -3).toString();
        }

        User user = this.app.getUserManager().getUser(data.getUserId(), session.getUserId());
        if (user == null) {
            log.warn("/user_profile: User [" + data.getUserId() + "] not found");
            return new ErrorResponse("user not found", -4).toString();
        }

        return new UserProfileResponse(user).toString();
    }

    // view a user's posts
    @POST(value="/user_posts", responseType= ResponseType.JSON)
    public String getUserPosts(@Body UserPostsRequest data) {
        if (data == null) {
            log.warn("/user_posts: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            return new ErrorResponse("session not found", -3).toString();
        }

        ArrayList<User> users = new ArrayList<>();
        ArrayList<Post> posts = new ArrayList<Post>();

        ArrayList<String> userIds = new ArrayList<String>(Arrays.asList(data.getUserIds()));
        users = this.app.getUserManager().getUsers(userIds, session.getUserId());
        if (users == null) {
            log.warn("/user_posts: Failed to load users");
            return new ErrorResponse("failed to load users", -9).toString();
        }

        posts = this.app.getPostManager().getUserPosts(users, session.getUserId());
        if (posts == null) {
            log.warn("/user_posts: Failed to load user posts");
            return new ErrorResponse("failed to load user posts", -10).toString();
        }

        // sort posts by date
        Collections.sort(posts, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getTimestamp().compareTo(p1.getTimestamp());
            }
        });

        return new UserPostsResponse(posts).toString();
    }

    // create a post
    @POST(value="/create_post", responseType= ResponseType.JSON)
    public String createPost(@Body CreatePostRequest data) {
        if (data == null) {
            log.warn("/create_post: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/create_post: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        String postId = this.app.getPostManager().createPost(session.getUserId(), data.getText(), data.hasImage());
        if (postId == null) {
            log.warn("/create_post: Failed to create post");
            return new ErrorResponse("failed to create post", -6).toString();
        }

        return new CreatePostResponse(postId).toString();
    }

    @POST(value="/delete_post", responseType= ResponseType.JSON)
    public String deletePost(@Body ItemActionRequest data) {
        if (data == null) {
            log.warn("/delete_post: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/delete_post: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        if (!this.app.getPostManager().deletePost(session.getUserId(), data.getItemId())) {
            log.warn("/delete_post: Failed to delete post");
            return new ErrorResponse("failed to delete post", -10).toString();
        }

        return new SuccessResponse().toString();
    }

    @POST(value="/like_post", responseType= ResponseType.JSON)
    public String likePost(@Body ItemActionRequest data) {
        if (data == null) {
            log.warn("/like_post: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/like_post: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        if (!this.app.getLikeManager().likePost(session.getUserId(), data.getItemId())) {
            log.warn("/like_post: Failed to like post");
            return new ErrorResponse("like post failed", -7).toString();
        }

        return new SuccessResponse().toString();
    }

    @POST(value="/unlike_post", responseType= ResponseType.JSON)
    public String unlikePost(@Body ItemActionRequest data) {
        if (data == null) {
            log.warn("/unlike_post: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/unlike_post: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        if (!this.app.getLikeManager().unlikePost(session.getUserId(), data.getItemId())) {
            log.warn("/unlike_post: Failed to like post");
            return new ErrorResponse("unlike post failed", -8).toString();
        }

        return new SuccessResponse().toString();
    }
}

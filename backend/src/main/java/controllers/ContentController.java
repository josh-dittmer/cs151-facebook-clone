package controllers;

import app.*;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@Path
public class ContentController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private SessionManager sessionManager;
    private PostManager postManager;
    private UserManager userManager;
    private LikeManager likeManager;

    public ContentController(SessionManager sessionManager, PostManager postManager, UserManager userManager, LikeManager likeManager) {
        this.sessionManager = sessionManager;
        this.postManager = postManager;
        this.userManager = userManager;
        this.likeManager = likeManager;
    }

    @POST(value="/user_profile", responseType= ResponseType.JSON)
    public String viewUserProfile(@Body UserProfileRequest data) {
        if (data == null) {
            log.warn("/user_profile: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        Session session = this.sessionManager.validateSession(data.getToken());
        if (session == null) {
            return new GenericError("session not found", -3).toString();
        }

        User user = this.userManager.getUser((data.getUserId().equals("me")) ? session.getUserId() : data.getUserId());
        if (user == null) {
            log.warn("/user_profile: User [" + data.getUserId() + "] not found");
            return new GenericError("user not found", -4).toString();
        }

        if (user.getUserId().equals(session.getUserId())) {
            user.setIsMyProfile(true);
        }

        return new UserProfileResponse(user).toString();
    }

    // view a user's posts
    @POST(value="/user_posts", responseType= ResponseType.JSON)
    public String viewUserPosts(@Body UserPostsRequest data) {
        if (data == null) {
            log.warn("/user_posts: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        Session session = this.sessionManager.validateSession(data.getToken());
        if (session == null) {
            return new GenericError("session not found", -3).toString();
        }

        ArrayList<Post> allPosts = new ArrayList<Post>();

        for (String userId : data.getUserIds()) {
            User user = this.userManager.getUser((userId.equals("me")) ? session.getUserId() : userId);

            if (user == null) {
                log.warn("/user_posts: User [" + userId + "] not found");
            } else {
                ArrayList<Post> userPosts = this.postManager.getUserPosts(user);
                if (userPosts == null) {
                    log.warn("/user_posts: Failed to load user posts for [" + userId + "]");
                } else {
                    allPosts.addAll(userPosts);
                }
            }
        }

        for (Post post : allPosts) {
            post.setLiked(this.likeManager.checkLiked(session.getUserId(), post.getPostId()));
        }

        // sort posts by date
        Collections.sort(allPosts, new Comparator<Post>() {
            @Override
            public int compare(Post p1, Post p2) {
                return p2.getTimestamp().compareTo(p1.getTimestamp());
            }
        });

        return new UserPostsResponse(allPosts).toString();
    }

    // create a post
    @POST(value="/create_post", responseType= ResponseType.JSON)
    public String createPost(@Body CreatePostRequest data) {
        if (data == null) {
            log.warn("/create_post: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        Session session = this.sessionManager.validateSession(data.getToken());
        if (session == null) {
            log.warn("/create_post: Session not found");
            return new GenericError("session not found", -3).toString();
        }

        String postId = this.postManager.createPost(session.getUserId(), data.getText(), data.hasImage());
        if (postId == null) {
            log.warn("/create_post: Failed to create post");
            return new GenericError("failed to create post", -6).toString();
        }

        return new CreatePostResponse(postId).toString();
    }

    @POST(value="/like_post", responseType= ResponseType.JSON)
    public String likePost(@Body LikePostRequest data) {
        if (data == null) {
            log.warn("/like_post: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        Session session = this.sessionManager.validateSession(data.getToken());
        if (session == null) {
            log.warn("/like_post: Session not found");
            return new GenericError("session not found", -3).toString();
        }

        if (!this.likeManager.likePost(session.getUserId(), data.getPostId())) {
            log.warn("/like_post: Failed to like post");
            return new GenericError("like post failed", -7).toString();
        }

        return new GenericSuccess().toString();
    }

    @POST(value="/unlike_post", responseType= ResponseType.JSON)
    public String unlikePost(@Body LikePostRequest data) {
        if (data == null) {
            log.warn("/unlike_post: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        Session session = this.sessionManager.validateSession(data.getToken());
        if (session == null) {
            log.warn("/unlike_post: Session not found");
            return new GenericError("session not found", -3).toString();
        }

        if (!this.likeManager.unlikePost(session.getUserId(), data.getPostId())) {
            log.warn("/unlike_post: Failed to like post");
            return new GenericError("unlike post failed", -8).toString();
        }

        return new GenericSuccess().toString();
    }
}

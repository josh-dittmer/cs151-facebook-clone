package controllers;

import app.Post;
import app.PostManager;
import app.SessionManager;
import app.UserManager;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class ContentController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private SessionManager sessionManager;
    private PostManager postManager;
    private UserManager userManager;

    public ContentController(SessionManager sessionManager, PostManager postManager, UserManager userManager) {
        this.sessionManager = sessionManager;
        this.postManager = postManager;
        this.userManager = userManager;
    }

    // view a user's posts
    @POST(value="/user_posts", responseType= ResponseType.JSON)
    public String viewUserPosts(@Body UserPostsRequest data) {
        if (data == null) {
            log.warn("/posts: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        return new PostListResponse(new ArrayList<Post>()).toString();
    }

    // create a post
    @POST(value="/create_post", responseType= ResponseType.JSON)
    public String createPost(@Body PostRequest data) {
        if (data == null) {
            log.warn("/create_post: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        return new PostListResponse(new ArrayList<Post>()).toString();
    }
}

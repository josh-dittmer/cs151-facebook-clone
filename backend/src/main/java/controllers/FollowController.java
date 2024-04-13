package controllers;

import app.Application;
import app.Post;
import app.Session;
import app.User;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.UserPostsRequest;
import controllers.json.UserPostsResponse;
import controllers.json.generic.ErrorResponse;
import controllers.json.generic.ItemActionRequest;
import controllers.json.generic.SuccessResponse;
import controllers.json.generic.UserListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

@Path
public class FollowController {
    private static final Logger log = LoggerFactory.getLogger(FollowController.class);

    private Application app;

    public FollowController(Application app) {
        this.app = app;
    }
    @POST(value="/follow_user", responseType= ResponseType.JSON)
    public String followUser(@Body ItemActionRequest data) {
        if (data == null) {
            log.warn("/follow_user: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/follow_user: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        if (!this.app.getFollowManager().followUser(session.getUserId(), data.getItemId())) {
            log.warn("/follow_user: Failed to follow user");
            return new ErrorResponse("follow user failed", -11).toString();
        }

        return new SuccessResponse().toString();
    }

    @POST(value="/unfollow_user", responseType= ResponseType.JSON)
    public String unfollowUser(@Body ItemActionRequest data) {
        if (data == null) {
            log.warn("/unfollow_user: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/unfollow_user: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        if (!this.app.getFollowManager().unfollowUser(session.getUserId(), data.getItemId())) {
            log.warn("/unfollow_user: Failed to follow user");
            return new ErrorResponse("unfollow user failed", -12).toString();
        }

        return new SuccessResponse().toString();
    }

    @POST(value="/user_following", responseType= ResponseType.JSON)
    public String getUserFollowing(@Body ItemActionRequest data) {
        if (data == null) {
            log.warn("/user_following: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            return new ErrorResponse("session not found", -3).toString();
        }

        ArrayList<User> users = new ArrayList<User>();

        users = this.app.getFollowManager().getUserFollowing(data.getItemId(), session.getUserId());
        if (users == null) {
            log.warn("/user_following: Failed to load user's following");
            return new ErrorResponse("failed to load user's following", -13).toString();
        }

        return new UserListResponse(users).toString();
    }
}

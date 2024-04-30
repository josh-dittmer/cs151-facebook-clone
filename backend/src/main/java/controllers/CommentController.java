package controllers;

import app.Application;
import app.Session;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.*;
import controllers.json.generic.ErrorResponse;
import controllers.json.generic.ItemActionRequest;
import controllers.json.generic.SuccessResponse;
import org.h2.command.dml.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private Application app;

    public CommentController(Application app) {
        this.app = app;
    }

    // create a comment
    @POST(value="/create_comment", responseType= ResponseType.JSON)
    public String createPost(@Body CreateCommentRequest data) {
        if (data == null) {
            log.warn("/create_comment: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/create_comment: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        String commentId = this.app.getCommentManager().createComment(data.getPostId(), session.getUserId(), data.getText());
        if (commentId == null) {
            log.warn("/create_comment: Failed to create comment");
            return new ErrorResponse("failed to create comment", -18).toString();
        }

        return new CreateCommentResponse(commentId).toString();
    }

    @POST(value="/delete_comment", responseType= ResponseType.JSON)
    public String deletePost(@Body DeleteCommentRequest data) {
        if (data == null) {
            log.warn("/delete_comment: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/delete_comment: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        if (!this.app.getCommentManager().deleteComment(session.getUserId(), data.getPostId(), data.getCommentId())) {
            log.warn("/delete_comment: Failed to delete comment");
            return new ErrorResponse("failed to delete comment", -10).toString();
        }

        return new SuccessResponse().toString();
    }
}

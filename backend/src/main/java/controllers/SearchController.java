package controllers;

import app.*;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.*;
import controllers.json.generic.ErrorResponse;
import controllers.json.generic.UserListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

@Path
public class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    private Application app;

    public SearchController(Application app) {
        this.app = app;
    }

    @POST(value="/search", responseType= ResponseType.JSON)
    public String search(@Body SearchRequest data) {
        if (data == null) {
            log.warn("/search: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().validateSession(data.getToken());
        if (session == null) {
            log.warn("/search: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        ArrayList<User> results = this.app.getSearchManager().search(data.getQuery(), session.getUserId());
        if (results == null) {
            log.warn("/search: Search failed");
            return new ErrorResponse("search failed", -9).toString();
        }

        return new UserListResponse(results).toString();
    }


}

package controllers;

import app.Session;
import app.SessionManager;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.RouteContext;
import com.hellokaton.blade.mvc.ui.ResponseType;
import com.hellokaton.blade.mvc.ui.RestResponse;
import controllers.json.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private SessionManager sessionManager;

    public LoginController(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }
    @POST(value="/login", responseType=ResponseType.JSON)
    public String login(@Body LoginRequest data) {
        if (data == null) {
            log.warn("/login: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        Session session = sessionManager.createSession(data.getUsername(), data.getPassword());
        if (session == null) {
            log.warn("/login: Could not create session");
            return new GenericError("invalid credentials", -2).toString();
        }

        log.info("/login: Successfully created session [" + session.getToken() + "]");
        return new LoginResponse(session.getToken()).toString();
    }

    @POST(value="/logout", responseType=ResponseType.JSON)
    public String logout(@Body GenericRequest data) {
        if (data == null) {
            log.warn("/logout: Request had invalid parameters");
            return new GenericError("invalid parameters", -1).toString();
        }

        Session session = sessionManager.deleteSession(data.getToken());
        if (session == null) {
            log.warn("/logout: Could not find session");
            return new GenericError("session not found", -3).toString();
        }

        log.info("/logout: Successfully deleted session [" + session.getToken() + "]");
        return new GenericSuccess().toString();
    }
}

package controllers;

import app.*;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Body;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.*;
import controllers.json.generic.ErrorResponse;
import controllers.json.generic.GenericRequest;
import controllers.json.generic.SuccessResponse;
import controllers.json.generic.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path
public class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    private Application app;

    public LoginController(Application app) {
        this.app = app;
    }
    @POST(value="/login", responseType=ResponseType.JSON)
    public String login(@Body LoginRequest data) {
        if (data == null) {
            log.warn("/login: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().createSession(data.getUsername(), data.getPassword());
        if (session == null) {
            log.warn("/login: Could not create session");
            return new ErrorResponse("invalid credentials", -2).toString();
        }

        log.info("/login: Successfully created session [" + session.getToken() + "]");
        return new TokenResponse(session.getToken()).toString();
    }

    @POST(value="/logout", responseType=ResponseType.JSON)
    public String logout(@Body GenericRequest data) {
        if (data == null) {
            log.warn("/logout: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Session session = this.app.getSessionManager().deleteSession(data.getToken());
        if (session == null) {
            log.warn("/logout: Could not find session");
            return new ErrorResponse("session not found", -3).toString();
        }

        log.info("/logout: Successfully deleted session [" + session.getToken() + "]");
        return new SuccessResponse().toString();
    }

    @POST(value="/signup", responseType=ResponseType.JSON)
    public String signup(@Body SignupRequest data) {
        if (data == null) {
            log.warn("/signup: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        Pattern p = Pattern.compile("^[a-zA-Z0-9._\\-]{3,}$");
        Matcher m = p.matcher(data.getUsername());
        if (!m.matches()) {
            return new ErrorResponse("bad characters in username", -19).toString();
        }

        if (data.getUsername().length() > 32 || data.getDisplayName().length() > 32 || data.getBio().length() > 512) {
            return new ErrorResponse("field too long", -20).toString();
        }

        if (data.getUsername().isEmpty() || data.getPassword().isEmpty()) {
            return new ErrorResponse("field is empty", -21).toString();
        }

        if (this.app.getUserManager().getUserByUsername(data.getUsername(), "") != null) {
            return new ErrorResponse("username taken", -22).toString();
        }

        User user = this.app.getUserManager().createUser(data.getUsername(), data.getPassword(), data.getDisplayName(), data.getBio());
        if (user == null) {
            log.warn("/signup: Failed to create user");
            return new ErrorResponse("user create failed", -9).toString();
        }

        Session session = this.app.getSessionManager().createSession(data.getUsername(), data.getPassword());
        if (session == null) {
            log.warn("/signup: Could not create session");
            return new ErrorResponse("invalid credentials", -2).toString();
        }

        return new TokenResponse(session.getToken()).toString();
    }
}

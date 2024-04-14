package controllers;

import app.Application;
import app.Resource;
import app.Session;
import com.hellokaton.blade.annotation.Path;
import com.hellokaton.blade.annotation.request.Form;
import com.hellokaton.blade.annotation.request.Multipart;
import com.hellokaton.blade.annotation.request.PathParam;
import com.hellokaton.blade.annotation.request.Query;
import com.hellokaton.blade.annotation.route.GET;
import com.hellokaton.blade.annotation.route.POST;
import com.hellokaton.blade.mvc.http.Response;
import com.hellokaton.blade.mvc.multipart.FileItem;
import com.hellokaton.blade.mvc.ui.ResponseType;
import controllers.json.generic.ErrorResponse;
import controllers.json.generic.SuccessResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RandomUID;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Path
public class ResourceController {
    private static final Logger log = LoggerFactory.getLogger(ResourceController.class);

    private Application app;

    public ResourceController(Application app) {
        this.app = app;
    }

    @POST(value="/upload", responseType= ResponseType.JSON)
    public String uploadResource(@Multipart FileItem file, @Form String associatedId, @Form String token){
        if (file == null || associatedId == null || token == null) {
            log.warn("/upload: Request had invalid parameters");
            return new ErrorResponse("invalid parameters", -1).toString();
        }

        if (file.getLength() > 300000000) {
            log.warn("/upload: File too big");
            return new ErrorResponse("file too big", -14).toString();
        }

        Session session = this.app.getSessionManager().validateSession(token);
        if (session == null) {
            log.warn("/upload: Session not found");
            return new ErrorResponse("session not found", -3).toString();
        }

        String fileExtension;
        switch(file.getContentType()) {
            case "image/gif":
                fileExtension = "gif";
                break;
            case "image/jpeg":
                fileExtension = "jpg";
                break;
            case "image/png":
                fileExtension = "png";
                break;
            default:
                log.warn("/upload: Invalid file type");
                return new ErrorResponse("invalid file type", -15).toString();
        }

        String resourceId = RandomUID.generate(64);

        String path;
        if (associatedId.equals("profile_pic")) {
            path = "uploads/profile_pics/";
        } else {
            path = "uploads/posts/";
        }

        path += resourceId + "." + fileExtension;

        try {
            file.moveTo(new File(path));
        } catch(Exception e) {
            log.warn("/upload: Failed to write file to disk");
            return new ErrorResponse("upload server error", -16).toString();
        }

        // we have a ResourceManager and separate resource table just in case we want to
        // support posts with multiple images in the future
        if (!this.app.getResourceManager().addResource(resourceId, session.getUserId(), associatedId, false, path)) {
            log.warn("/upload: Failed to add resource to database");
            return new ErrorResponse("resource upload failed", -17).toString();
        }

        return new SuccessResponse().toString();
    }

    // in the future this will take a resourceId that can be looked up with /get_resources
    @GET(value="/resource/:associatedId", responseType=ResponseType.PREVIEW)
    public void getResource(Response response, @PathParam String associatedId, @Query String s) {
        if (associatedId == null || s == null) {
            log.warn("/resource: Request had invalid parameters");
            response.badRequest();
            return;
        }

        String filePath;

        Session session = this.app.getSessionManager().validateSession(s);
        if (session == null) {
            log.warn("/upload: Session not found");
            response.unauthorized();
            return;
        }

        Resource resource = this.app.getResourceManager().getResource(associatedId);
        if (resource == null) {
            log.warn("/upload: Resource not found");
            filePath = "./static/not_found.png";
        } else if (resource.isRemoteResource()) {
            log.info("Remote resource, sending redirect");
            response.redirect(resource.getResourceLocation());
            return;
        } else {
            filePath = "./" + resource.getResourceLocation();
        }

        try {
            log.info("Local resource, sending file");
            response.write(new File(filePath));
        } catch(IOException e) {
            log.warn("/upload: Failed to open resource");
            response.notFound();
        }
    }


}

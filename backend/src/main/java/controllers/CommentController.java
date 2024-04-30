package controllers;

import app.Application;
import com.hellokaton.blade.annotation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path
public class CommentController {
    private static final Logger log = LoggerFactory.getLogger(CommentController.class);

    private Application app;

    public CommentController(Application app) {
        this.app = app;
    }
}

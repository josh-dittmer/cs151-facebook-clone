package controllers;

import app.*;
import com.hellokaton.blade.annotation.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path
public class SearchController {
    private static final Logger log = LoggerFactory.getLogger(SearchController.class);

    private Application app;

    public SearchController(Application app) {
        this.app = app;
    }


}

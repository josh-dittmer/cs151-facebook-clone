package app;

import com.hellokaton.blade.Blade;
import com.hellokaton.blade.options.CorsOptions;
import com.hellokaton.blade.options.HttpOptions;

import controllers.ContentController;
import controllers.LoginController;
import controllers.SearchController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private DatabaseConnection databaseConn;

    private SessionManager sessionManager;
    private PostManager postManager;
    private UserManager userManager;
    private LikeManager likeManager;
    private SearchManager searchManager;

    private LoginController loginController;
    private ContentController contentController;
    private SearchController searchController;

    public Application() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./db/cs151-facebook-clone", "sa", "");
        this.databaseConn = new DatabaseConnection(conn);

        // database must be created
        if (this.databaseConn.numTables() != 5) {
            log.warn("Database does not exist! Creating...");
            this.databaseConn.createDb();
            log.info("Database created successfully!");
        }

        this.sessionManager = new SessionManager(this);
        this.postManager = new PostManager(this);
        this.userManager = new UserManager(this);
        this.likeManager = new LikeManager(this);
        this.searchManager = new SearchManager(this);

        this.loginController = new LoginController(this);
        this.contentController = new ContentController(this);
        this.searchController = new SearchController(this);
    }

    public DatabaseConnection getDatabaseConn() {
        return databaseConn;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public PostManager getPostManager() {
        return postManager;
    }

    public UserManager getUserManager() {
        return userManager;
    }

    public LikeManager getLikeManager() {
        return likeManager;
    }
    public SearchManager getSearchManager() { return searchManager; }

    public LoginController getLoginController() {
        return loginController;
    }

    public ContentController getContentController() {
        return contentController;
    }

    public SearchController getSearchController() {
        return searchController;
    }

    public static void main(String[] args) {
        Application app;

        try {
            app = new Application();
        } catch(Exception e) {
            log.error("Failed to create app: " + e.getMessage());
            return;
        }

        log.info("Database connection successful!");

        log.info("Starting Blade initialization...");

        CorsOptions corsOptions = CorsOptions.forAnyOrigin().allowNullOrigin().allowCredentials().allowedHeaders("Content-Type");
        HttpOptions httpOptions = HttpOptions.create().enableSession();

        Blade blade = Blade.create();
        blade.cors(corsOptions);
        blade.http(httpOptions);

        blade.scanPackages("controllers");
        blade.register(app.getLoginController());
        blade.register(app.getContentController());
        blade.register(app.getSearchController());

        blade.start(args);
    }
}

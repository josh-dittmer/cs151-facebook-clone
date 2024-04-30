package app;

import com.hellokaton.blade.Blade;
import com.hellokaton.blade.options.CorsOptions;
import com.hellokaton.blade.options.HttpOptions;

import controllers.*;
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
    private MessageManager messageManager;
    private SearchManager searchManager;
    private FollowManager followManager;
    private ResourceManager resourceManager;
    private CommentManager commentManager;

    private LoginController loginController;
    private ContentController contentController;
    private SearchController searchController;
    private FollowController followController;
    private ResourceController resourceController;
    private CommentController commentController;

    public Application() throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:h2:./db/cs151-facebook-clone", "sa", "");
        this.databaseConn = new DatabaseConnection(conn);

        // database must be created
        if (this.databaseConn.numTables() != 7) {
            log.warn("Database does not exist! Creating...");
            this.databaseConn.createDb();
            log.info("Database created successfully!");
        }

        this.sessionManager = new SessionManager(this);
        this.postManager = new PostManager(this);
        this.userManager = new UserManager(this);
        this.likeManager = new LikeManager(this);
        this.messageManager = new MessageManager(this);
        this.searchManager = new SearchManager(this);
        this.followManager = new FollowManager(this);
        this.resourceManager = new ResourceManager(this);
        this.commentManager = new CommentManager(this);

        this.loginController = new LoginController(this);
        this.contentController = new ContentController(this);
        this.searchController = new SearchController(this);
        this.followController = new FollowController(this);
        this.resourceController = new ResourceController(this);
        this.commentController = new CommentController(this);
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
    public CommentManager getCommentManager() {
        return commentManager;
    }

    public MessageManager getMessageManager() { return messageManager; }
    public LikeManager getLikeManager() {
        return likeManager;
    }
    public SearchManager getSearchManager() { return searchManager; }
    public FollowManager getFollowManager() { return followManager; }
    public ResourceManager getResourceManager() { return resourceManager; }

    public LoginController getLoginController() {
        return loginController;
    }

    public ContentController getContentController() {
        return contentController;
    }

    public SearchController getSearchController() {
        return searchController;
    }
    public FollowController getFollowController() { return followController; }
    public ResourceController getResourceController() { return resourceController; }
    public CommentController getCommentController() { return commentController; }

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
        blade.listen(9000);

        blade.scanPackages("controllers");
        blade.register(app.getLoginController());
        blade.register(app.getContentController());
        blade.register(app.getSearchController());
        blade.register(app.getFollowController());
        blade.register(app.getResourceController());
        blade.register(app.getCommentController());

        blade.start(args);
    }
}

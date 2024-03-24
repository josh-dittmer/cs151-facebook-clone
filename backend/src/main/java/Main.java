import app.DatabaseConnection;
import app.PostManager;
import app.SessionManager;
import app.UserManager;
import com.hellokaton.blade.Blade;
import com.hellokaton.blade.options.CorsOptions;
import com.hellokaton.blade.options.HttpOptions;

import controllers.ContentController;
import controllers.LoginController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        DatabaseConnection databaseConn;

        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:./db/cs151-facebook-clone", "sa", "");
            databaseConn = new DatabaseConnection(conn);
        } catch(SQLException e) {
            log.error("Failed to connect to database: " + e.getMessage());
            return;
        }

        log.info("Database connection successful!");

        // app classes
        SessionManager sessionManager = new SessionManager(databaseConn);
        PostManager postManager = new PostManager(databaseConn);
        UserManager userManager = new UserManager(databaseConn);

        // api controllers
        LoginController loginController = new LoginController(sessionManager);
        ContentController contentController = new ContentController(sessionManager, postManager, userManager);

        log.info("Starting Blade initialization...");

        CorsOptions corsOptions = CorsOptions.forAnyOrigin().allowNullOrigin().allowCredentials().allowedHeaders("Content-Type");
        HttpOptions httpOptions = HttpOptions.create().enableSession();

        Blade blade = Blade.create();
        blade.cors(corsOptions);
        blade.http(httpOptions);

        blade.scanPackages("controllers");
        blade.register(loginController);
        blade.register(contentController);

        blade.start(args);
    }
}

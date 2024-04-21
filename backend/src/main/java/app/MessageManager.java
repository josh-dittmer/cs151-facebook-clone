package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MessageManager {
    private static final Logger log = LoggerFactory.getLogger(MessageManager.class);
    private Application app;
    public MessageManager(Application app) {
        this.app = app;
    }



}

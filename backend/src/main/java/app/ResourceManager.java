package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.RandomUID;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ResourceManager {
    private static final Logger log = LoggerFactory.getLogger(ResourceManager.class);

    private Application app;

    public ResourceManager(Application app) {
        this.app = app;
    }

    public boolean addResource(String resourceId, String userId, String associatedId, boolean isRemoteResource, String resourceLocation) {
        try {
            Map<String, String> resourceData = new HashMap<String, String>();
            resourceData.put("RESOURCE_ID", resourceId);
            resourceData.put("USER_ID", userId);
            resourceData.put("ASSOCIATED_ID", associatedId);
            resourceData.put("IS_REMOTE_RESOURCE", (isRemoteResource) ? "TRUE" : "FALSE");
            resourceData.put("RESOURCE_LOCATION", resourceLocation);

            this.app.getDatabaseConn().insert("UPLOADS", resourceData);
        } catch(SQLException e) {
            log.error("SQL error while adding resource: " + e.getMessage());
            return false;
        }

        return true;
    }

    public Resource getResource(String associatedId) {
        Resource resource;

        try {
            ResultSet resultSet = this.app.getDatabaseConn().lookup("UPLOADS", "ASSOCIATED_ID", associatedId);

            if (!resultSet.next()) {
                return null;
            }

            String resourceId = resultSet.getString("RESOURCE_ID");
            String resourceUserId = resultSet.getString("USER_ID");
            String resourceAssociatedId = resultSet.getString("ASSOCIATED_ID");
            boolean resourceIsRemote = resultSet.getBoolean("IS_REMOTE_RESOURCE");
            String resourceLocation = resultSet.getString("RESOURCE_LOCATION");
            Timestamp resourceTimestamp = resultSet.getTimestamp("TIMESTAMP");

            resource = new Resource(resourceId, resourceUserId, resourceAssociatedId, resourceIsRemote, resourceLocation, resourceTimestamp);
        } catch(SQLException e) {
            log.error("SQL error while getting resource: " + e.getMessage());
            return null;
        }

        return resource;
    }

    public Resource deleteResource(String associatedId) {
        Resource resource;

        try {
            resource = this.getResource(associatedId);
            if (associatedId == null) {
                return null;
            }

            Map<String, String> criteria = new HashMap<String, String>();
            criteria.put("ASSOCIATED_ID", associatedId);
            this.app.getDatabaseConn().delete("UPLOADS", criteria);
        } catch(SQLException e) {
            log.error("SQL error while deleting resource: " + e.getMessage());
            return null;
        }

        if (!resource.isRemoteResource()) {
            File file = new File("./" + resource.getResourceLocation());
            if (!file.delete()) {
                log.error("Failed to delete resource file [" + resource.getResourceId() + "]");
            }
        }

        log.info("Deleted resource [" + resource.getResourceId() + "] from database");

        return resource;
    }
}

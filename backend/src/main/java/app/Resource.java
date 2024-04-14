package app;

import java.sql.Timestamp;

public class Resource {
    private String resourceId;
    private String userId;
    private String associatedId;
    private boolean isRemoteResource;
    private String resourceLocation;
    private Timestamp timestamp;

    public Resource(String resourceId, String userId, String associatedId, boolean isRemoteResource, String resourceLocation, Timestamp timestamp) {
        this.resourceId = resourceId;
        this.userId = userId;
        this.associatedId = associatedId;
        this.isRemoteResource = isRemoteResource;
        this.resourceLocation = resourceLocation;
        this.timestamp = timestamp;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssociatedId() {
        return associatedId;
    }

    public void setAssociatedId(String associatedId) {
        this.associatedId = associatedId;
    }

    public boolean isRemoteResource() {
        return isRemoteResource;
    }

    public void setRemoteResource(boolean remoteResource) {
        isRemoteResource = remoteResource;
    }

    public String getResourceLocation() {
        return resourceLocation;
    }

    public void setResourceLocation(String resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

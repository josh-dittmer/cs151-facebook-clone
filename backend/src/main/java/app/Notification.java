package app;

import java.sql.Timestamp;
public class Notification {

    private String notification;
    private String userId;
    private String username;
    private String displayName;
    private String text;
    private Timestamp timestamp;

    public Notification(String notification, String userId, String username, String displayName, String text, Timestamp timestamp) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

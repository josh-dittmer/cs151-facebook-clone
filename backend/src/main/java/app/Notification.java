package app;

import java.sql.Timestamp;
public class Notification {

    private String userId;
    private String username;
    private String displayName;
    private String text;

    public Notification(String userId, String username, String displayName, String text) {
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.text = text;
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
}

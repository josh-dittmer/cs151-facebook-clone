package app;

import java.sql.Timestamp;

public class Message {

    private String message;
    private String messageId;
    private String userId;
    private String username;
    private String displayName;
    private Timestamp timestamp;

    public Message(String message, String messageId, String userId, String username, String displayName, Timestamp timestamp) {
        this.message = message;
        this.messageId = messageId;
        this.userId = userId;
        this.username = username;
        this.displayName = displayName;
        this.timestamp = timestamp;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}

package controllers.json;

public class PostRequest {
    private String text;
    private boolean hasImage;
    private String token;

    public PostRequest() {
        this.text = "";
        this.hasImage = false;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean hasImage() {
        return hasImage;
    }

    public void setHasImage(boolean hasImage) {
        this.hasImage = hasImage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

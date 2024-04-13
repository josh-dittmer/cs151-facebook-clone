package controllers.json.generic;

public class ItemActionRequest {
    private String itemId;

    private String token;

    public ItemActionRequest() {
        this.itemId = "";
        this.token = "";
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

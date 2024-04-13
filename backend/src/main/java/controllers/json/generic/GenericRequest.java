package controllers.json.generic;

public class GenericRequest {
    private String token;

    public GenericRequest() {
        this.token = "";
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}

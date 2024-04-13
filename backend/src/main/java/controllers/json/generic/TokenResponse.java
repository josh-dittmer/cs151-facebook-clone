package controllers.json.generic;

public class TokenResponse {
    private String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    @Override
    public String toString() {
        return "{\"success\":true,\"token\":\"" + token + "\"}";
    }
}

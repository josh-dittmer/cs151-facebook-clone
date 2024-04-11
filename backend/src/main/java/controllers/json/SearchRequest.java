package controllers.json;

public class SearchRequest {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    private String query;
    private String token;

    public SearchRequest() {
        this.query = "";
        this.token = "";
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}

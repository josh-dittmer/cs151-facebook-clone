package controllers.json.generic;

public class ErrorResponse {
    private String message;
    private int code;

    public ErrorResponse(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    @Override
    public String toString() {
        return "{\"success\":false,\"message\":\"" + message + "\",\"code\":" + code + "}";
    }
}

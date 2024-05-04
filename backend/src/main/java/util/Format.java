package util;

public class Format {
    public static String jsonEscape(String str) {
        return str.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}

package util;

import java.security.SecureRandom;

public class RandomUID {
    public static SecureRandom rand = new SecureRandom();
    public static char[] symbols = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();

    public static String generate(int length) {
        char[] buf = new char[length];

        for (int i = 0; i < length; i++) {
            buf[i] = symbols[rand.nextInt(symbols.length)];
        }

        return new String(buf);
    }

}

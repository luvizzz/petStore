package utils;

import java.util.Random;

public class Utils {
    public static int randomId() {
        Random random = new Random();
        return random.nextInt(9999999);
    }

    public static String randomStatus() {
        Random random = new Random();
        return "SomeStatus" + random.nextInt(9999999);
    }
}

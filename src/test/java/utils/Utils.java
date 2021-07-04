package utils;

import domain.Tag;

import java.util.Random;

public class Utils {
    static Random random = new Random();

    public static int randomId() {
        return random.nextInt(9999999);
    }

    public static String randomStatus() {
        return "SomeStatus" + random.nextInt(9999999);
    }

    public static Tag randomTag() {
        int tagId = random.nextInt(9999999);
        return new Tag(tagId, "someRandomTag" + tagId);
    }
}

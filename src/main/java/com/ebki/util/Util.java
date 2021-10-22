package com.ebki.util;

import java.util.Random;

public class Util {

    public static void throwIllegalStateException (String message) {
        throw new IllegalStateException(message);
    }

    public static Long generateID(int bound) {
        Random random = new Random();
        return (long) random.nextInt(bound);
    }

    public static boolean isIntegerValueZero(Integer integer) {
        return integer.equals(0);
    }

    public static boolean isLongValueZero(Long value) {
        return value.compareTo(0L) == 0;
    }
}

package com.box.lib.utils;

import java.util.Random;

/**
 * Description
 *
 * @author Linsr 2018/12/9 下午4:49
 */
public class NumberUtils {

    private static Random sRandom = new Random();
    private static final int DEFAULT_NEXT_INT_BOUND = 10000;

    public static String getRandomNumberStr() {
        return String.valueOf(getRandomNumberStr(DEFAULT_NEXT_INT_BOUND));
    }

    public static String getRandomNumberStr(int bound) {
        return String.valueOf(getRandomNumber(bound));
    }

    public static int getRandomNumber() {
        return getRandomNumber(DEFAULT_NEXT_INT_BOUND);
    }

    public static int getRandomNumber(int bound) {
        return sRandom.nextInt(bound);
    }
}

package com.triofantastico.practiceproject.helper;

import java.util.concurrent.ThreadLocalRandom;

public final class RandomGenerator {

    private RandomGenerator() {
        throw new AssertionError("Instantiation attempted from within class");
    }

    public static Integer getRandomNumberFromRange(int min, int max) {
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return min + threadLocalRandom.nextInt(max);
    }

    public static Integer getRandomNumberPositiveNumberUpTo(int max) {
        int min = 1;
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        return min + threadLocalRandom.nextInt(max);
    }
}
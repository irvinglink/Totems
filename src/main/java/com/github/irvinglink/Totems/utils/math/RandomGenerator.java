package com.github.irvinglink.Totems.utils.math;

import java.util.concurrent.ThreadLocalRandom;

public class RandomGenerator {

    public static boolean generateBoolean(int chance, int bound) {
        int doubleChance = ThreadLocalRandom.current().nextInt(bound);
        return doubleChance < chance;
    }

}

package com.airdnb.clone.dummy.stay;

import com.airdnb.clone.dummy.RandomNumberGenerator;

public class DummyTravelerGenerator {

    private static final int MIN_TRAVELER = 1;
    private static final int MAX_TRAVELER = 20;

    public static int generate() {
        return RandomNumberGenerator.generateInt(MIN_TRAVELER, MAX_TRAVELER);
    }
}

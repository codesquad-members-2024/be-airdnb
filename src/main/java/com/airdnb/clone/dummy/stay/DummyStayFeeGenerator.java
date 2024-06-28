package com.airdnb.clone.dummy.stay;

import com.airdnb.clone.dummy.RandomNumberGenerator;

public class DummyStayFeeGenerator {

    private static final long MIN_PER_NIGHT_RATE = 10_000L;
    private static final long MAX_PER_NIGHT_RATE = 1_000_000L;
    private static final long MIN_CLEANING_FEE = 10_000L;
    private static final long MAX_CLEANING_FEE = 200_000L;
    private static final int INTERVAL = 5_000;

    public static Long generatePerNightRate() {
        return RandomNumberGenerator.generateLongByInterval(MIN_PER_NIGHT_RATE, MAX_PER_NIGHT_RATE, INTERVAL);
    }

    public static Long generateCleaningFee() {
        return RandomNumberGenerator.generateLongByInterval(MIN_CLEANING_FEE, MAX_CLEANING_FEE, INTERVAL);
    }
}

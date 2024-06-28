package com.airdnb.clone.dummy.stay;

import com.airdnb.clone.dummy.RandomNumberGenerator;

public class DummyFacilityGenerator {

    private static final int MIN_BED_ROOM = 1;
    private static final int MAX_BED_ROOM = 5;
    private static final int MIN_BATH_ROOM = 1;
    private static final int MAX_BATH_ROOM = 5;
    private static final int MIN_BED = 1;
    private static final int MAX_BED = 20;

    public static int generateBedRoomCount() {
        return RandomNumberGenerator.generateInt(MIN_BED_ROOM, MAX_BED_ROOM);
    }

    public static int generateBathRoomCount() {
        return RandomNumberGenerator.generateInt(MIN_BATH_ROOM, MAX_BATH_ROOM);
    }

    public static int generateBedCount() {
        return RandomNumberGenerator.generateInt(MIN_BED, MAX_BED);
    }
}

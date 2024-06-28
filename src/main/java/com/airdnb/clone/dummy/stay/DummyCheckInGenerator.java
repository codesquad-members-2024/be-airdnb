package com.airdnb.clone.dummy.stay;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class DummyCheckInGenerator {
    private static final List<LocalTime> checkInTimes = List.of(
            LocalTime.of(14, 0),
            LocalTime.of(15,0),
            LocalTime.of(16,0),
            LocalTime.of(17,0));

    public static LocalTime generate() {
        return checkInTimes.get(new Random().nextInt(0, checkInTimes.size()));
    }
}

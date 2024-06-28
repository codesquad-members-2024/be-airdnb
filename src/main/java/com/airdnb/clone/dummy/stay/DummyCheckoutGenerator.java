package com.airdnb.clone.dummy.stay;

import java.time.LocalTime;
import java.util.List;
import java.util.Random;

public class DummyCheckoutGenerator {

    private static final List<LocalTime> checkOutTimes = List.of(
            LocalTime.of(10, 0),
            LocalTime.of(11, 0),
            LocalTime.of(12, 0));

    public static LocalTime generate() {
        return checkOutTimes.get(new Random().nextInt(0, checkOutTimes.size()));
    }
}

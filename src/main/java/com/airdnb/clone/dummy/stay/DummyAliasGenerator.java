package com.airdnb.clone.dummy.stay;

import java.util.concurrent.atomic.AtomicInteger;

public class DummyAliasGenerator {

    private static final String name = "숙소";
    private static final AtomicInteger SEQ = new AtomicInteger(1);
    private static final int INCREMENT_STEP = 1;

    public static String generate() {
        return name + SEQ.getAndAdd(INCREMENT_STEP);
    }
}

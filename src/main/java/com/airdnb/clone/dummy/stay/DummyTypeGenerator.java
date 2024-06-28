package com.airdnb.clone.dummy.stay;

import com.airdnb.clone.domain.stay.entity.Stay.Type;
import com.airdnb.clone.dummy.RandomNumberGenerator;

public class DummyTypeGenerator {

    public static Type generate() {
        int first = RandomNumberGenerator.generateInt(0, Type.values().length);

        return Type.values()[first];
    }
}

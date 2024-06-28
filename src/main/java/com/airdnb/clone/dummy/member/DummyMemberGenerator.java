package com.airdnb.clone.dummy.member;

import com.airdnb.clone.domain.member.entity.Member;
import java.util.concurrent.atomic.AtomicInteger;

public class DummyMemberGenerator {

    private static final AtomicInteger SEQ = new AtomicInteger(1);
    private static final int INCREMENT_STEP = 1;

    public static Member generate() {
        return Member.builder()
                .loginId("dummyMember" + SEQ.getAndAdd(INCREMENT_STEP))
                .name("멤버" + SEQ.get())
                .build();
    }
}

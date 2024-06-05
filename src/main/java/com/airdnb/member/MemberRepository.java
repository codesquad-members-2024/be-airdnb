package com.airdnb.member;

import com.airdnb.member.entity.Member;
import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(String id);

    void save(Member member);
}
package com.airdnb.member;

import com.airdnb.member.entity.Member;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
@Transactional
public class MemberJpaRepository implements MemberRepository {
    private final EntityManager em;

    @Override
    public Optional<Member> findById(String id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }
}

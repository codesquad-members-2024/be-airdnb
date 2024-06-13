package com.airdnb.clone.domain.member;

import com.airdnb.clone.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}

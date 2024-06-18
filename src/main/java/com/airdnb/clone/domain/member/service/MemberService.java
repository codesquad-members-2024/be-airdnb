package com.airdnb.clone.domain.member.service;

import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(Member member) {
        String hashPassword = passwordEncoder.encode(member.getPassword());
        member.changePassword(hashPassword);
        memberRepository.save(member);
    }
}

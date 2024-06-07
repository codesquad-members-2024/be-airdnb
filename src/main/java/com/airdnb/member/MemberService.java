package com.airdnb.member;

import com.airdnb.global.NotFoundException;
import com.airdnb.member.dto.MemberRegistration;
import com.airdnb.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;

    public Member findMemberById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("id와 일치하는 유저가 존재하지 않습니다."));
    }

    public void saveMember(MemberRegistration memberRegistration) {
        Member member = memberMapper.toMember(memberRegistration);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
    }

}

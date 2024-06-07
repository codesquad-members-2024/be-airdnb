package com.airdnb.member;

import com.airdnb.global.NotFoundException;
import com.airdnb.member.dto.MemberRegistration;
import com.airdnb.member.dto.MemberVerification;
import com.airdnb.member.entity.Member;
import com.airdnb.security.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberMapper memberMapper;
    private final JwtUtil jwtUtil;

    public Member findMemberById(String id) {
        return memberRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("id와 일치하는 유저가 존재하지 않습니다."));
    }

    public void saveMember(Member member) {
        memberRepository.save(member);
    }

    public void register(MemberRegistration memberRegistration) {
        Member member = memberMapper.toMember(memberRegistration);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        saveMember(member);
    }

    public String verify(MemberVerification memberVerification) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                memberVerification.getId(),
                memberVerification.getPassword()
            )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Member member = memberRepository.findById(memberVerification.getId())
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        String token = jwtUtil.createToken(member.getId());
        return token;
    }


}





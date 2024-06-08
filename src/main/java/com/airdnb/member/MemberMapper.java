package com.airdnb.member;

import com.airdnb.member.entity.Member;
import com.airdnb.member.dto.MemberRegistration;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toMember(MemberRegistration memberRegistration) {
        return Member.builder()
            .id(memberRegistration.getId())
            .name(memberRegistration.getName())
            .password(memberRegistration.getPassword())
            .role(memberRegistration.getRole())
            .build();
    }

    public MemberRegistration toMemberRegistration(Member member) {
        return MemberRegistration.builder()
            .id(member.getId())
            .name(member.getName())
            .password(member.getPassword())
            .role(member.getRole())
            .build();
    }

}

package com.airdnb.clone.domain.member.controller.dto;

import com.airdnb.clone.domain.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberJoinRequest {

    @NotBlank
    private final String loginId;

    @NotBlank
    private final String password;

    @NotBlank
    private final String role;

    private final String phoneNumber;

    public Member toMember() {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .role(role)
                .phoneNumber(phoneNumber)
                .build();
    }
}

package com.yourbnb.member.model.dto;

import com.yourbnb.member.model.Member;
import lombok.Getter;

@Getter
public class MemberResponse {

    private String memberId;

    private MemberResponse(String memberId) {
        this.memberId = memberId;
    }

    public static MemberResponse from(Member member) {
        return new MemberResponse(member.getMemberId());
    }
}

package com.yourbnb.member.model.dto;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.member.model.Member;
import com.yourbnb.reservation.model.Reservation;
import com.yourbnb.review.model.Review;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

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

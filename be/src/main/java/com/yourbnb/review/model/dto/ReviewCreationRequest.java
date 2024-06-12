package com.yourbnb.review.model.dto;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.member.model.Member;
import com.yourbnb.review.model.Review;

public record ReviewCreationRequest(String content,
                                    double rate,
                                    String memberId,
                                    Long accommodationId) {

    public Review toEntity(Member member, Accommodation accommodation) {
        return Review.of(content, rate, member, accommodation);
    }
}

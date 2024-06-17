package com.example.airdnb.dto.review;

import com.example.airdnb.domain.review.Review;

public record ReviewResponse(
    Long id,
    String userName,
    Double rating,
    String comment
) {

    public static ReviewResponse fromEntity(Review review) {
        return new ReviewResponse(review.getId(), review.getUser().getName(), review.getRating(), review.getComment());
    }


}

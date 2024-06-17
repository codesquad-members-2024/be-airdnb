package com.example.airdnb.dto.review;

import com.example.airdnb.domain.accommodation.Accommodation;
import com.example.airdnb.domain.review.Review;
import com.example.airdnb.domain.user.User;
import com.example.airdnb.validation.ValidRating;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ReviewCreateRequest(
    @NotNull
    @ValidRating
    Double rating,
    @NotBlank
    String comment
) {

    public Review toEntity(User user, Accommodation accommodation) {
        return Review.builder()
            .user(user)
            .accommodation(accommodation)
            .rating(rating)
            .comment(comment)
            .build();
    }

}

package team10.airdnb.review.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.member.entity.Member;
import team10.airdnb.review.entity.Review;

public record ReviewCreateRequest(
        @NotBlank
        String memberId,
        @NotNull
        Long accommodationId,
        String comment,
        @NotNull
        @Min(0)
        @Max(5)
        Double rate
) {
    public Review toEntity(Member member, Accommodation accommodation) {
        return Review.builder()
                .member(member)
                .accommodation(accommodation)
                .comment(comment)
                .rate(rate)
                .build();
    }
}

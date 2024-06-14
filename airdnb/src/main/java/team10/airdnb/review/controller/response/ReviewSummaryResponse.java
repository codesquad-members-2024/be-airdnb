package team10.airdnb.review.controller.response;

import team10.airdnb.accommodation.dto.AccommodationInformationDto;
import team10.airdnb.member.dto.MemberInformationDto;
import team10.airdnb.review.entity.Review;

public record ReviewSummaryResponse(
        long reviewId,
        MemberInformationDto memberInformation,
        AccommodationInformationDto accommodationInformation,
        String comment,
        double rate
) {
    public static ReviewSummaryResponse from(Review review) {
        return new ReviewSummaryResponse(
                review.getId(),
                MemberInformationDto.from(review.getMember()),
                AccommodationInformationDto.from(review.getAccommodation()),
                review.getComment(),
                review.getRate()
        );
    }

}

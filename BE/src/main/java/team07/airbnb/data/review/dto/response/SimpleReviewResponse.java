package team07.airbnb.data.review.dto.response;

import team07.airbnb.data.user.dto.response.SimpleUserResponse;
import team07.airbnb.entity.ReviewEntity;

public record SimpleReviewResponse(
        Long id,
        SimpleUserResponse writer,
        String content,
        int rating
) {

    public static SimpleReviewResponse of(ReviewEntity review) {
        return new SimpleReviewResponse(
                review.getId(),
                SimpleUserResponse.of(review.getWriter()),
                review.getContent(),
                review.getRating()
        );
    }
}

package team07.airbnb.data.review.dto.response;

import team07.airbnb.data.user.dto.response.SimpleUserResponse;
import team07.airbnb.entity.ReviewEntity;

import java.util.List;

public record ReviewWithReplyResponse(
        Long id,
        SimpleUserResponse writer,
        String content,
        int rating,
        List<ReplyResponse> replies
) {

    public static ReviewWithReplyResponse of(ReviewEntity review) {
        return new ReviewWithReplyResponse(
                review.getId(),
                SimpleUserResponse.of(review.getWriter()),
                review.getContent(),
                review.getRating(),
                review.getReplies().stream().map(ReplyResponse::of).toList()
        );
    }
}

package team07.airbnb.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.not_found.ReviewNotFoundException;
import team07.airbnb.repository.ReviewRepository;
import team07.airbnb.service.booking.BookingInquiryService;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingInquiryService bookingInquiryService;

    public void addReplyTo(long reviewId, String content, UserEntity writer) {
        ReviewEntity review = getById(reviewId);

        if (!bookingInquiryService.isUserHostOrBookerOf(review.getBooking().getId(), writer)) {
            throw new UnAuthorizedException(this.getClass(), writer.getId(), "ID : {%d} 유저가 권한 없는 댓글 작성 시도".formatted(writer.getId()));
        }

        review.addReply(content, writer);
    }

    public boolean isWriterOf(Long reviewId, Long userId) {
        ReviewEntity review = getById(reviewId);
        return review.getWriter().getId().equals(userId);
    }

    public void updateReviewContent(Long reviewId, String content, Long userId) {
        ReviewEntity review = getById(reviewId);

        if (!isWriterOf(reviewId, userId)) {
            throw new UnAuthorizedException(this.getClass(), userId, "ID : {%d} 유저가 자신의 리뷰가 아닌 리뷰를 수정하려고함".formatted(userId));
        }
        review.setContent(content);
        reviewRepository.save(review);
    }

    private ReviewEntity getById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
    }
}

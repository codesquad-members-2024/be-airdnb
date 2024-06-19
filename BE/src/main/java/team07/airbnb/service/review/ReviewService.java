package team07.airbnb.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.controller.ReviewController;
import team07.airbnb.data.booking.enums.CheckAuthType;
import team07.airbnb.data.review.dto.request.ReviewPostRequest;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.not_found.ReviewNotFoundException;
import team07.airbnb.repository.BookingRepository;
import team07.airbnb.repository.ReviewRepository;
import team07.airbnb.service.booking.BookingAuthService;
import team07.airbnb.service.booking.BookingInquiryService;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final BookingRepository bookingRepository;
    private final BookingAuthService bookingAuthService;
    private final BookingInquiryService bookingInquiryService;

    public void addReplyTo(long reviewId, String content, UserEntity writer) {
        ReviewEntity review = getById(reviewId);

        bookingAuthService.currentUserIsSameWith(review.getBooking().getId(), writer, CheckAuthType.ALL);

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

    public void postReview(Long userId, Long bookingId, ReviewPostRequest request) {
        BookingEntity booking = bookingInquiryService.findByBookingId(bookingId);
        this.addReview(
                bookingId,
                userId,
                new ReviewEntity(booking, request.content(), request.rating())
        );
    }


    private void addReview(Long bookingId, Long writerId, ReviewEntity review) {
        BookingEntity booking = bookingInquiryService.findByBookingId(bookingId);
        if (!booking.getBooker().getId().equals(writerId))
            throw new UnAuthorizedException(ReviewController.class, writerId, "%d 번 예약의 예약자만 리뷰를 작성할 수 있습니다!".formatted(bookingId));

        bookingRepository.save(booking.addReview(review));
    }
}

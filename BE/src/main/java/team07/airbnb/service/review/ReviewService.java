package team07.airbnb.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.not_found.ReviewNotFoundException;
import team07.airbnb.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void addReplyTo(long reviewId, String content, UserEntity writer) {
        // 해당 예약의 HOST인지 확인 필요 
    }

    public boolean isWriterOf(Long reviewId, UserEntity user) {
        ReviewEntity review = getById(reviewId);
        return review.getWriter().equals(user);
    }

    public void updateReviewContent(Long reviewId, String content) {
        ReviewEntity review = getById(reviewId);
        review.setContent(content);

        reviewRepository.save(review);
    }

    private ReviewEntity getById(Long id) {
        return reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id));
    }
}

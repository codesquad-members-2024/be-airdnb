package codesquad.team05.service;

import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.review.Review;
import codesquad.team05.domain.review.ReviewRepository;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.web.review.dto.request.ReviewSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public Long writeReview(Long userId, Long accommodationId, ReviewSave reviewSave) {
        return reviewRepository.save(new Review(
                reviewSave.getRating(),
                reviewSave.getComment(),
                userRepository.findById(userId).get(),
                accommodationRepository.findById(accommodationId).get()
        )).getId();
    }
}

package team03.airdnb.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.accommodation.AccommodationService;
import team03.airdnb.exception.AccommodationNotFoundException;
import team03.airdnb.exception.ErrorCode;
import team03.airdnb.exception.ReviewNotFoundException;
import team03.airdnb.exception.UserNotFoundException;
import team03.airdnb.review.dto.request.ReviewSaveDto;
import team03.airdnb.review.dto.request.ReviewUpdateDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationService accommodationService;

    public Long createReview(ReviewSaveDto reviewSaveDto) {
        // accommodation averageGrade 업데이트
        accommodationService.updateAverageGradeOnReviewAdd(reviewSaveDto.getAccommodationId(), reviewSaveDto.getGrade());

        User user = userRepository.findById(reviewSaveDto.getUserId()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Accommodation accommodation = accommodationRepository.findById(reviewSaveDto.getAccommodationId()).orElseThrow(() -> new AccommodationNotFoundException(ErrorCode.ACCOMMODATION_NOT_FOUND));
        Review savedReview = reviewRepository.save(reviewSaveDto.toEntity(user, accommodation));
        return savedReview.getId();
    }

    public void updateReview(ReviewUpdateDto reviewUpdateDto) {
        if(reviewUpdateDto.isGradeUpdated()){
            // accommodation averageGrade 업데이트
            accommodationService.updateAverageGradeOnReviewUpdate(reviewUpdateDto.getAccommodationId(), reviewUpdateDto.getGradeDifference());
        }

        User user = userRepository.findById(reviewUpdateDto.getUserId()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Accommodation accommodation = accommodationRepository.findById(reviewUpdateDto.getAccommodationId()).orElseThrow(() -> new AccommodationNotFoundException(ErrorCode.ACCOMMODATION_NOT_FOUND));
        reviewRepository.save(reviewUpdateDto.toEntity(user, accommodation));
    }

    public void deleteReview(Long reviewId) {
        Review reviewToDelete = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(ErrorCode.REVIEW_NOT_FOUND));
        reviewRepository.deleteById(reviewId);

        // accommodation averageGrade 업데이트
        accommodationService.updateAverageGradeOnReviewDelete(reviewToDelete.getAccommodation().getId(), reviewToDelete.getGrade());
    }
}

package team03.airdnb.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.accommodation.AccommodationService;
import team03.airdnb.review.dto.request.ReviewSaveDto;
import team03.airdnb.review.dto.request.ReviewUpdateDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;
    private final AccommodationService accommodationService;

    public Review createReview(ReviewSaveDto reviewSaveDto) {
        // accommodation averageGrade 업데이트
        accommodationService.updateAverageGradeOnReviewAdd(reviewSaveDto.getAccommodationId(), reviewSaveDto.getGrade());

        User user = userRepository.findById(reviewSaveDto.getUserId()).get(); // 예외처리 추가할 예정입니다!
        Accommodation accommodation = accommodationRepository.findById(reviewSaveDto.getAccommodationId()).get(); // 예외처리 추가할 예정입니다!
        return reviewRepository.save(reviewSaveDto.toEntity(user, accommodation));
    }

    public void updateReview(ReviewUpdateDto reviewUpdateDto) {
        if(reviewUpdateDto.isGradeUpdated()){
            // accommodation averageGrade 업데이트
            accommodationService.updateAverageGradeOnReviewUpdate(reviewUpdateDto.getAccommodationId(), reviewUpdateDto.getGradeDifference());
        }

        User user = userRepository.findById(reviewUpdateDto.getUserId()).get(); // 예외처리 추가할 예정입니다!
        Accommodation accommodation = accommodationRepository.findById(reviewUpdateDto.getAccommodationId()).get(); // 예외처리 추가할 예정입니다!
        reviewRepository.save(reviewUpdateDto.toEntity(user, accommodation));
    }

    public void deleteReview(Long reviewId) {
        Review reviewToDelete = reviewRepository.findById(reviewId).get();
        reviewRepository.deleteById(reviewId);

        // accommodation averageGrade 업데이트
        accommodationService.updateAverageGradeOnReviewDelete(reviewToDelete.getAccommodation().getId(), reviewToDelete.getGrade());
    }
}

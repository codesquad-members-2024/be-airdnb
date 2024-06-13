package team10.airdnb.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.exception.AccommodationIdNotFoundException;
import team10.airdnb.accommodation.repository.AccommodationRepository;
import team10.airdnb.member.entity.Member;
import team10.airdnb.member.exception.MemberIdNotFoundException;
import team10.airdnb.member.repository.MemberRepository;
import team10.airdnb.review.controller.request.ReviewCreateRequest;
import team10.airdnb.review.controller.request.ReviewUpdateRequest;
import team10.airdnb.review.controller.response.ReviewSummaryResponse;
import team10.airdnb.review.entity.Review;
import team10.airdnb.review.exception.ReviewIdNotFoundException;
import team10.airdnb.review.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    public List<ReviewSummaryResponse> getReviews() {
        return reviewRepository.findAll().stream()
                .map(ReviewSummaryResponse::from)
                .collect(Collectors.toList());
    }

    public ReviewSummaryResponse createReview(ReviewCreateRequest request) {
        Member member = getMemberById(request.memberId());

        Accommodation accommodation = getAccommodationById(request.accommodationId());

        Review review = request.toEntity(member, accommodation);

        reviewRepository.save(review);

        return ReviewSummaryResponse.from(review);
    }

    public ReviewSummaryResponse updateReview(long reviewId, ReviewUpdateRequest request) {
        Review review = getReviewById(reviewId);

        review.updateReview(request.comment(), request.rate());

        reviewRepository.save(review);

        return ReviewSummaryResponse.from(review);
    }

    public ReviewSummaryResponse deleteReview(long reviewId) {
        Review review = getReviewById(reviewId);

        reviewRepository.delete(review);

        return ReviewSummaryResponse.from(review);
    }

    private Review getReviewById(long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(ReviewIdNotFoundException::new);
    }

    private Member getMemberById(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(MemberIdNotFoundException::new);
    }

    private Accommodation getAccommodationById(long accommodationId) {
        return accommodationRepository.findById(accommodationId)
                .orElseThrow(AccommodationIdNotFoundException::new);
    }
}

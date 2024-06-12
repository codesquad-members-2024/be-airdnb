package com.yourbnb.review.service;

import com.yourbnb.accommodation.model.Accommodation;
import com.yourbnb.accommodation.repository.AccommodationRepository;
import com.yourbnb.member.model.Member;
import com.yourbnb.member.repository.MemberRepository;
import com.yourbnb.review.model.Review;
import com.yourbnb.review.model.dto.ReviewCreationRequest;
import com.yourbnb.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final AccommodationRepository accommodationRepository;

    public Review createReview(ReviewCreationRequest reviewCreationRequest) {
        // 1) DTO -> Entity 변환
        Member member = memberRepository.findById(reviewCreationRequest.memberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Accommodation accommodation = accommodationRepository.findById(reviewCreationRequest.accommodationId())
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        Review review = reviewCreationRequest.toEntity(member,accommodation);

        // 2) 저장 및 반환
        return reviewRepository.save(review);
    }


}

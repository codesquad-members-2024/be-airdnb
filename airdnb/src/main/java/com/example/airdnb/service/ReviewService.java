package com.example.airdnb.service;

import com.example.airdnb.domain.accommodation.Accommodation;
import com.example.airdnb.domain.review.Review;
import com.example.airdnb.domain.user.User;
import com.example.airdnb.dto.review.ReviewCreateRequest;
import com.example.airdnb.dto.review.ReviewResponse;
import com.example.airdnb.repository.AccommodationRepository;
import com.example.airdnb.repository.ReviewRepository;
import com.example.airdnb.repository.UserRepository;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public ReviewResponse create(String email, Long accommodationId, ReviewCreateRequest request) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(NoSuchElementException::new);
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
            .orElseThrow(NoSuchElementException::new);

        Review review = request.toEntity(user, accommodation);
        Review savedReview = reviewRepository.save(review);
        return ReviewResponse.fromEntity(savedReview);
    }

    public List<ReviewResponse> getAllReviews(Long accommodationId) {
        List<Review> reviews = reviewRepository.findAllByAccommodationId(accommodationId);
        return reviews.stream()
            .map(ReviewResponse::fromEntity)
            .toList();
    }
}

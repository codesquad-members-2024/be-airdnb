package com.yourbnb.review.controller;

import com.yourbnb.review.model.Review;
import com.yourbnb.review.model.dto.ReviewCreationRequest;
import com.yourbnb.review.model.dto.ReviewCreationResponse;
import com.yourbnb.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewCreationResponse> createReview(@RequestBody ReviewCreationRequest request) {
        Review createdReview = reviewService.createReview(request);

        ReviewCreationResponse response = ReviewCreationResponse.from(createdReview);

        return ResponseEntity.ok(response);
    }
}

package com.yourbnb.review.model.dto;

import com.yourbnb.review.model.Review;
import lombok.Getter;

@Getter
public class ReviewCreationResponse {

    private static final String CREATE_SUCCESS_MESSAGE_FORMAT = "리뷰 생성 성공! 리뷰 #%d 내용 : %s";

    private final Long id;
    private final String content;
    private final String message;

    private ReviewCreationResponse(Long id, String content, String message) {
        this.id = id;
        this.content = content;
        this.message = message;
    }

    public static ReviewCreationResponse from(Review review) {
        Long id = review.getId();
        String content = review.getContent();
        String message = generateSuccessMessage(review);
        return new ReviewCreationResponse(id, content, message);
    }

    private static String generateSuccessMessage(Review review) {
        return String.format(CREATE_SUCCESS_MESSAGE_FORMAT, review.getId(), review.getContent());
    }
}

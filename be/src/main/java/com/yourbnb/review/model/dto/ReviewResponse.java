package com.yourbnb.review.model.dto;

import com.yourbnb.review.model.Review;
import lombok.Getter;

@Getter
public class ReviewResponse {
    Long id;
    String content;
    Double rate;


    private ReviewResponse(Long id, String content, Double rate) {
        this.id = id;
        this.content = content;
        this.rate = rate;
    }

    public static ReviewResponse from (Review review){
        return new ReviewResponse(
                review.getId(),
                review.getContent(),
                review.getRate()
        );
    }
}

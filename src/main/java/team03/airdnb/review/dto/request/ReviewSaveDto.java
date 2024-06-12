package team03.airdnb.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.review.Review;
import team03.airdnb.user.User;

@Getter
@AllArgsConstructor
public class ReviewSaveDto {

    private String content;
    private double grade;
    private Long userId;
    private Long accommodationId;

    public Review toEntity(User user, Accommodation accommodation) {
        return Review.builder()
                .content(this.content)
                .grade(this.grade)
                .user(user)
                .accommodation(accommodation)
                .build();
    }
}

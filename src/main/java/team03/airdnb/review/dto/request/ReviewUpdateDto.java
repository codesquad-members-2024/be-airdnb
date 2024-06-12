package team03.airdnb.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.review.Review;
import team03.airdnb.user.User;

@Getter
@AllArgsConstructor
public class ReviewUpdateDto {

    private Long id;
    private String content;
    private double previousGrade;
    private double updatedGrade; // grade가 수정되지 않았다면 기존의 grade 입력
    private Long userId;
    private Long accommodationId;

    public Review toEntity(User user, Accommodation accommodation) {
        return Review.builder()
                .id(this.id)
                .content(this.content)
                .grade(this.updatedGrade)
                .user(user)
                .accommodation(accommodation)
                .build();
    }

    public boolean isGradeUpdated() {
        return !(previousGrade == updatedGrade);
    }

    public double getGradeDifference() {
        return updatedGrade - previousGrade;
    }
}

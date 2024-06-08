package team03.airdnb.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.review.Review;
import team03.airdnb.user.dto.response.UserShowDto;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewShowDto {

    private Long id;
    private String content;
    private double grade;
    private UserShowDto user;
    private LocalDateTime createdAt;

    public static ReviewShowDto of(Review review){
        return new ReviewShowDto(
                review.getId(),
                review.getContent(),
                review.getGrade(),
                UserShowDto.of(review.getUser()),
                review.getCreatedAt()
        );
    }
}

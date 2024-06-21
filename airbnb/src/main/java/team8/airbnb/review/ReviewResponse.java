package team8.airbnb.review;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponse {
  private Long id;
  private Long hostroomId;
  private Long userId;
  private String content;
  private int rating;

  public ReviewResponse(Review review) {
    this.id = review.getId();
    this.hostroomId = review.getHostroom().getId();
    this.userId = review.getUser().getId();
    this.content = review.getContent();
    this.rating = review.getRating();
  }
}

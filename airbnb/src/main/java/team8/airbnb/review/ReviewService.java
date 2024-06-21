package team8.airbnb.review;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team8.airbnb.hostroom.Hostroom;
import team8.airbnb.hostroom.HostroomRepository;
import team8.airbnb.user.User;
import team8.airbnb.user.UserRepository;

@Service
public class ReviewService {

  @Autowired
  private ReviewRepository reviewRepository;

  @Autowired
  private HostroomRepository hostroomRepository;

  @Autowired
  private UserRepository userRepository;

  public List<ReviewResponse> getAllReviewsByHostroomId(Long hostroomId) {
    Hostroom hostroom = hostroomRepository.findById(hostroomId)
        .orElseThrow(() -> new RuntimeException("Hostroom not found with id: " + hostroomId));
    List<Review> reviews = reviewRepository.findByHostroom(hostroom);
    return reviews.stream().map(ReviewResponse::new).collect(Collectors.toList());
  }

  public ReviewResponse createReview(Long hostroomId, Long userId, String content, int rating) {
    Hostroom hostroom = hostroomRepository.findById(hostroomId)
        .orElseThrow(() -> new RuntimeException("Hostroom not found with id: " + hostroomId));
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

    Review review = new Review();
    review.setHostroom(hostroom);
    review.setUser(user);
    review.setContent(content);
    review.setRating(rating);
    Review savedReview = reviewRepository.save(review);
    return new ReviewResponse(savedReview);
  }

  public ReviewResponse updateReview(Long hostroomId, Long reviewId, String content, int rating) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
    if (!review.getHostroom().getId().equals(hostroomId)) {
      throw new RuntimeException("Review does not belong to the specified hostroom");
    }
    review.setContent(content);
    review.setRating(rating);
    Review updatedReview = reviewRepository.save(review);
    return new ReviewResponse(updatedReview);
  }

  public void deleteReview(Long hostroomId, Long reviewId) {
    Review review = reviewRepository.findById(reviewId)
        .orElseThrow(() -> new RuntimeException("Review not found with id: " + reviewId));
    if (!review.getHostroom().getId().equals(hostroomId)) {
      throw new RuntimeException("Review does not belong to the specified hostroom");
    }
    reviewRepository.deleteById(reviewId);
  }
}

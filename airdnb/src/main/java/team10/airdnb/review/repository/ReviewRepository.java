package team10.airdnb.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.review.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}

package team07.airbnb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team07.airbnb.entity.ReviewEntity;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}

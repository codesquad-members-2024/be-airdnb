package com.team01.airdnb.comment;

import com.team01.airdnb.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
  Optional<List<Comment>> findByAccommodationId(Long accommodationId);

  @Query("SELECT ROUND(AVG(c.score), 1) FROM Comment c WHERE c.accommodation.id = :accommodationId")
  Double findAverageScoreByAccommodationId(@Param("accommodationId") Long accommodationId);

  @Query("SELECT ROUND(AVG(c.score), 1) FROM Comment c WHERE c.accommodation.user = :user")
  Double findAverageScoreByUser(@Param("user") User user);
}

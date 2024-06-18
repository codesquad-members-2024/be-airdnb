package com.team01.airdnb.comment;

import com.team01.airdnb.comment.dto.CommentShowResponse;
import com.team01.airdnb.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

  @Query("SELECT new com.team01.airdnb.comment.dto.CommentShowResponse(c.id, c.score, c.content, c.createdAt, c.user) " +
      "FROM Comment c WHERE c.accommodation.id = :accommodationId")
  List<CommentShowResponse> findAllByAccommodationId(@Param("accommodationId") Long accommodationId);

  @Query("SELECT ROUND(AVG(c.score), 1) FROM Comment c WHERE c.accommodation.id = :accommodationId")
  Double findAverageScoreByAccommodationId(@Param("accommodationId") Long accommodationId);

  @Query("SELECT ROUND(AVG(c.score), 1) FROM Comment c WHERE c.accommodation.user = :user")
  Double findAverageScoreByUser(@Param("user") User user);
}

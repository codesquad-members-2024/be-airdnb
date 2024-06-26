package com.team01.airdnb.accommadation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, AccommodationFilterRepository {
  List<Accommodation> findAllByUserId (@Param("userId") Long userId);

  @Query("UPDATE Accommodation a SET a.commentsNum = a.commentsNum + 1 WHERE a.id = :id")
  void incrementCommentsNum(@Param(("id")) Long id);

  @Query("UPDATE Accommodation a SET a.commentsNum = a.commentsNum - 1 WHERE a.id = :id")
  void decrementCommentsNum(@Param(("id")) Long id);


}

package com.team01.airdnb.accommadation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, AccommodationFilterRepository {
  @Query("SELECT a.id FROM Accommodation a WHERE a.user.id = :hostId")
  List<Long> findAllAccommodationIdsByHostId(@Param("hostId") Long hostId);
}

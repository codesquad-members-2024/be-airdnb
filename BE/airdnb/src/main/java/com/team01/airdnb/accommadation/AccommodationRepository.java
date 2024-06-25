package com.team01.airdnb.accommadation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, AccommodationFilterRepository {
  List<Accommodation> findAllByUserId (@Param("userId") Long userId);
}

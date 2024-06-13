package com.team01.airdnb.amenity;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
  Optional<Amenity> findByAccommodationId(Long accommodationId);
}

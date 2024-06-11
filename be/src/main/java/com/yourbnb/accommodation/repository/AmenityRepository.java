package com.yourbnb.accommodation.repository;

import com.yourbnb.accommodation.model.Amenity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {
    Set<Amenity> findAllByIdIsIn(Set<Long> amenityIds);
}

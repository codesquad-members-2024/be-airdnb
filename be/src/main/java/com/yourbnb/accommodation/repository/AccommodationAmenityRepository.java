package com.yourbnb.accommodation.repository;

import com.yourbnb.accommodation.model.AccommodationAmenity;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccommodationAmenityRepository extends JpaRepository<AccommodationAmenity, Long> {
    @Query("SELECT aa.amenities.id FROM AccommodationAmenity aa WHERE aa.accommodations.id = :accommodationId")
    Set<Long> findAmenitiesByAccommodationId(Long accommodationId);
}

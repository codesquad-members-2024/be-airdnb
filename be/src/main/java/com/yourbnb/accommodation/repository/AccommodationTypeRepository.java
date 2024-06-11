package com.yourbnb.accommodation.repository;

import com.yourbnb.accommodation.model.AccommodationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationTypeRepository extends JpaRepository<AccommodationType, Long> {
}

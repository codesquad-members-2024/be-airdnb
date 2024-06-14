package com.yourbnb.image.repository;

import com.yourbnb.image.model.AccommodationImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationImageRepository extends JpaRepository<AccommodationImage, Long> {
}

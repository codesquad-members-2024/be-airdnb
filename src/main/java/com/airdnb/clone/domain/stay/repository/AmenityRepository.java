package com.airdnb.clone.domain.stay.repository;

import com.airdnb.clone.domain.stay.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmenityRepository extends JpaRepository<Amenity, Long> {
}

package com.airdnb.clone.domain.stay.repository;

import com.airdnb.clone.domain.stay.entity.AvailableAmenity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailableAmenityRepository extends JpaRepository<AvailableAmenity, Long> {
}

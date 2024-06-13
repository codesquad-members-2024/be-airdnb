package com.airdnb.clone.domain.stay.repository;

import com.airdnb.clone.domain.stay.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {
}

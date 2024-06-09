package com.airdnb.stay.repository;

import com.airdnb.stay.entity.Stay;
import com.airdnb.stay.entity.StayStatus;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StayRepository extends JpaRepository<Stay, Long> {
    Optional<Stay> findByIdAndStatus(Long id, StayStatus status);
}

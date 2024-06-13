package com.airdnb.clone.domain.stay;

import com.airdnb.clone.domain.stay.entity.Stay;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StayRepository extends JpaRepository<Stay, Long> {
}

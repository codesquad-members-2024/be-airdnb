package com.airdnb.stay.repository;

import com.airdnb.stay.entity.Stay;
import java.util.Optional;

public interface StayRepository {
    Stay save(Stay stay);

    Optional<Stay> findById(Long id);
}

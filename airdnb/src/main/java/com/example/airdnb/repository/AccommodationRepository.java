package com.example.airdnb.repository;

import com.example.airdnb.domain.accommodation.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long>, AccommodationRepositoryCustom {

}

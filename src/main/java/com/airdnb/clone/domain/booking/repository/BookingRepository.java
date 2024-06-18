package com.airdnb.clone.domain.booking.repository;

import com.airdnb.clone.domain.booking.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>, BookingQuerydsl {
}

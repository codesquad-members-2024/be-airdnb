package com.example.airdnb.repository;


import com.example.airdnb.domain.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}

package com.airdnb.clone.domain.reservation;

import com.airdnb.clone.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}

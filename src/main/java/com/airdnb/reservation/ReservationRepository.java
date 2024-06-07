package com.airdnb.reservation;

import com.airdnb.reservation.entity.Reservation;
import java.util.Optional;

public interface ReservationRepository {
    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long id);
}

package team10.airdnb.reservation.repository;

import team10.airdnb.reservation.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface CustomReservationRepository {
    List<Reservation> findConflictingReservations(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate);
}

package team10.airdnb.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.reservation.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}

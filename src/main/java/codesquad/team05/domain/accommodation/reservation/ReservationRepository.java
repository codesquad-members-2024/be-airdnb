package codesquad.team05.domain.accommodation.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findReservationByAccommodationId(Long accommodationId);
}

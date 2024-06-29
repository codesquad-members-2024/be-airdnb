package team03.airdnb.reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT CASE WHEN COUNT(r) = 0 THEN TRUE ELSE FALSE END FROM Reservation r WHERE r.accommodation.id = :accommodationId AND (r.checkIn < :checkOut AND r.checkOut > :checkIn)")
    boolean isReservationAvailable(@Param("accommodationId") Long accommodationId, @Param("checkIn") LocalDate checkIn, @Param("checkOut") LocalDate checkOut);

    @Query("SELECT CASE WHEN COUNT(r) > 0 THEN TRUE ELSE FALSE END FROM Reservation r WHERE r.user.id = :userId AND r.accommodation.id = :accommodationId")
    boolean existsByUserAndAccommodation(@Param("userId") Long userId, @Param("accommodationId") Long accommodationId);
}

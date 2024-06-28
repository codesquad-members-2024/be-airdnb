package team8.airbnb.reservedroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservedroomRepository extends JpaRepository<Reservedroom, Long> {

  @Query("SELECT r FROM Reservedroom r WHERE r.hostroom.id = :hostroomId " +
      "AND ((r.checkinDate <= :endDate AND r.checkoutDate >= :startDate) " +
      "OR (r.checkinDate >= :startDate AND r.checkinDate < :endDate))")
  List<Reservedroom> findOverlappingReservations(
      @Param("hostroomId") Long hostroomId,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  List<Reservedroom> findByHostroomId(Long hostroomId);

  List<Reservedroom> findByGuestId(Long guestId);
}
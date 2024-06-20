package com.team01.airdnb.reservation;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  List<Reservation> findAllByAccommodationId(Long reservationId);

  @Query("SELECT COUNT(r) FROM Reservation r where r.id = :id and r.startDate >= :startDate and r.endDate <= :endDate")
  Integer countReservationById(@Param("id") Long id, @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}

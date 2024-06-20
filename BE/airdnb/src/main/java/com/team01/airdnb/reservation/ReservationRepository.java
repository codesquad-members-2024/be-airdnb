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

  @Query("SELECT count(a) FROM Accommodation a LEFT JOIN Reservation r ON a.id = r.accommodation.id "
      + "where a.id = :id AND (r.startDate between :startDate and :endDate "
      + "or r.endDate between :startDate and : endDate "
      + "or (r.startDate <= :startDate and r.endDate >= :endDate)"
      + "or (a.maxPets < :pets or a.maxInfants < :infants or a.maxChildren < :children or a.maxAdults < :adults))")
  Integer countReservationById(@Param("id") Long id, @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate, @Param("adults") Integer adults, @Param("children") Integer children,
      @Param("infants") Integer infants, @Param("pets") Integer pets);
}

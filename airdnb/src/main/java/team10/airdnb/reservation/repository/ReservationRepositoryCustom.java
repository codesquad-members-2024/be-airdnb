package team10.airdnb.reservation.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import team10.airdnb.reservation.dto.ReservationAccommodationDto;
import team10.airdnb.reservation.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepositoryCustom {
//    List<Reservation> findConflictingReservations(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    boolean isDateRangeAvailable(Long accommodationId, LocalDate checkInDate, LocalDate checkOutDate);

    List<ReservationAccommodationDto> findReservationAccommodationDTOsByMemberId(String memberId);
}


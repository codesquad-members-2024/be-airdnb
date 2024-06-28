package team10.airdnb.reservation.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ReservationAccommodationDto(
        Long reservationId,
        Boolean isConfirmed,
        Boolean deleted,
        LocalDate checkInDate,
        LocalDate checkOutDate,
        Integer capacity,
        BigDecimal totalPrice,
        String accommodationName,
        String accommodationDescription,
        String accommodationImages,
        String city,
        String district,
        String neighborhood,
        String streetName,
        String detailedAddress,
        String memberId
) {

}

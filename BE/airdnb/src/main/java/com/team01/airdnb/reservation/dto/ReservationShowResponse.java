package com.team01.airdnb.reservation.dto;

import java.time.LocalDate;
import lombok.Builder;

@Builder
public record ReservationShowResponse(
    Long reservationID,
    Long accommodationID,
    LocalDate startDate,
    LocalDate endDate,
    Integer adults,
    Integer children,
    Integer infants,
    Integer pets,
    Long price,
    Long userId
) {

}

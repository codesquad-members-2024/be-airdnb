package com.team01.airdnb.reservation.dto;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.reservation.Reservation;
import com.team01.airdnb.user.User;
import java.time.LocalDate;
import lombok.Getter;

public record ReservationRequest(
    Integer adults,
    Integer children,
    Integer infants,
    Integer pets,
    Long price,
    LocalDate startDate,
    LocalDate endDate,
    Long userId,
    Long accommodationId) {

  public Reservation toEntity(User user, Accommodation accommodation) {
    return Reservation.builder()
        .adults(this.adults)
        .children(this.children)
        .infants(this.infants)
        .pets(this.pets)
        .price(this.price)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .user(user)
        .accommodation(accommodation)
        .build();
  }
}

package com.team01.airdnb.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.reservation.Reservation;
import com.team01.airdnb.user.User;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class ReservationCreateRequest {
  private Long reservationId;
  private Integer adults;
  private Integer children;
  private Integer infants;
  private Integer pets;
  private Long price;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
  private LocalDate startDate;
  @JsonDeserialize(using = LocalDateDeserializer.class)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
  private LocalDate endDate;

  private String userId;

  private Long accommodationId;

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

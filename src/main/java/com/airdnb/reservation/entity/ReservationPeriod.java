package com.airdnb.reservation.entity;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReservationPeriod {
    private LocalDateTime checkinAt;
    private LocalDateTime checkoutAt;

    public long getDaysOfStay() {
        LocalDate checkinDate = checkinAt.toLocalDate();
        LocalDate checkoutDate = checkoutAt.toLocalDate();
        return ChronoUnit.DAYS.between(checkinDate, checkoutDate);
    }
}

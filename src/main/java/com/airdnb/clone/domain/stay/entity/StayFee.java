package com.airdnb.clone.domain.stay.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
@EqualsAndHashCode
public class StayFee {

    @Column(name = "PER_NIGHT")
    private Long perNight;

    @Column(name = "CLEANING_FEE")
    private Long cleaningFee;

    public Long calculateTotalRate(LocalDateTime checkIn, LocalDateTime checkOut) {
        validate(checkIn, checkOut);

        Duration duration = Duration.between(checkIn, checkOut);
        long totalDays = duration.toDays();

        return totalDays * perNight + cleaningFee;
    }

    private void validate(LocalDateTime checkIn, LocalDateTime checkOut) {
        if (checkIn.isAfter(checkOut)) {
            throw new IllegalArgumentException();
        }
    }
}

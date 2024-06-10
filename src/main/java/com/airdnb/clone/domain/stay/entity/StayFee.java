package com.airdnb.clone.domain.stay.entity;

import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class StayFee {

    private Integer perNight;
    private Integer cleaningFee;

    public void changePerNight(int perNight) {
        this.perNight = perNight;
    }

    public void changeCleaningFee(int cleaningFee) {
        this.cleaningFee = cleaningFee;
    }

    public Long calculateTotalRate(LocalDateTime checkIn, LocalDateTime checkOut) {
        Duration duration = Duration.between(checkIn, checkOut);
        long totalDays = duration.toDays();

        return totalDays * perNight + cleaningFee;
    }
}

package com.airbnb.domain.booking.dto.request;

import com.airbnb.domain.accommodation.entity.Accommodation;
import com.airbnb.domain.booking.entity.Booking;
import com.airbnb.domain.common.BookingStatus;
import com.airbnb.domain.member.entity.Member;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class BookingCreateRequest {

    @NotNull
    private LocalDate checkIn;

    @NotNull
    private LocalDate checkOut;

    @NotNull
    @Min(1)
    private Integer adults;
    private int children;

    @Max(5)
    private int infants;

    @NotNull
    private String cardName;

    public Booking toEntity(Member guest, Accommodation accommodation) {
        return Booking.builder()
            .guest(guest)
            .accommodation(accommodation)
            .checkIn(checkIn)
            .checkOut(checkOut)
            .adults(adults)
            .children(children)
            .infants(infants)
            .status(BookingStatus.REQUESTED)
            .build();
    }

    public boolean isUnderMaxGuests(int maxGuests) {
        return maxGuests >= adults + children;
    }

    public boolean isCheckInAfterToday() {
        return checkIn.isAfter(LocalDate.now());
    }

    public boolean isCheckOutAfterCheckIn() {
        return checkOut.isAfter(checkIn);
    }
}
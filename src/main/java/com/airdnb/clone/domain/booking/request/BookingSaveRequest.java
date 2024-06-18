package com.airdnb.clone.domain.booking.request;

import com.airdnb.clone.domain.booking.entity.Booking.BookingBuilder;
import com.airdnb.clone.domain.common.Guest;
import com.airdnb.clone.domain.booking.entity.Booking;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
public class BookingSaveRequest {

    @NotNull
    private final Long memberId;

    @NotNull
    private final Long stayId;

    @Future
    private final LocalDateTime checkIn;

    @Future
    private final LocalDateTime checkOut;

    @Min(value = 0)
    private final Long totalRate;

    @Min(value = 1)
    private final Integer guestCount;

    public BookingBuilder toBuilder() {
        return Booking.builder()
                .checkIn(checkIn)
                .checkOut(checkOut)
                .totalRate(totalRate)
                .guest(Guest.builder().guestCount(guestCount).build());
    }
}

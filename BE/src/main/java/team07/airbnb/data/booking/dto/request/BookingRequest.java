package team07.airbnb.data.booking.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import team07.airbnb.common.validation.CheckOutAfterCheckIn;

import java.time.LocalDate;

@CheckOutAfterCheckIn
public record BookingRequest(
        @NotNull
        @Positive
        Long accommodationId,
        @NotNull
        @FutureOrPresent
        LocalDate checkIn,
        @NotNull
        @FutureOrPresent
        LocalDate checkOut,
        @NotNull
        Integer headCount
) {
}

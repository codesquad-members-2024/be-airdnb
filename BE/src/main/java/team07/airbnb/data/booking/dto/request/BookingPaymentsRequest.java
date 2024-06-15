package team07.airbnb.data.booking.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import team07.airbnb.common.validation.CheckOutAfterCheckIn;

import java.time.LocalDate;

@CheckOutAfterCheckIn
public record BookingPaymentsRequest(
        @NotNull
        @FutureOrPresent
        LocalDate checkIn,
        @NotNull
        @FutureOrPresent
        LocalDate checkOut,
        @NotNull
        @Min(-180) @Max(180)
        Double longitude,
        @NotNull
        @Min(-90) @Max(90)
        Double latitude,
        @NotNull
        @Positive
        Double distance
) {
}

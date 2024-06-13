package team07.airbnb.data.booking.dto.request;

import jakarta.validation.constraints.NotNull;
import team07.airbnb.common.validation.CheckOutAfterCheckIn;

import java.time.LocalDate;

@CheckOutAfterCheckIn
public record BookingPaymentsRequest(
        @NotNull
        LocalDate checkIn,
        @NotNull
        LocalDate checkOut,
        @NotNull
        Double longitude,
        @NotNull
        Double latitude,
        @NotNull
        Double distance
) {
}

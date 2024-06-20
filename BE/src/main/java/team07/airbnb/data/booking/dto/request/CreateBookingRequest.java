package team07.airbnb.data.booking.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import team07.airbnb.common.validation.CheckOutAfterCheckIn;

import java.time.LocalDate;

@CheckOutAfterCheckIn
public record CreateBookingRequest(
        @NotNull
        Long accommodationId,
        @NotNull
        @Future
        LocalDate checkIn,
        @NotNull
        @Future
        LocalDate checkOut,
        @NotNull
        Integer headCount
) {
}

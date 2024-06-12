package team07.airbnb.data.booking.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;
import team07.airbnb.common.validation.CheckOutAfterCheckIn;

import java.time.LocalDate;

@CheckOutAfterCheckIn
public record BookingRequest(
        @NotNull
        Long accommodationId,
        @NotNull
        @Future
        LocalDate checkIn,
        @NotNull
        @Future
        LocalDate checkOut,
        @NotNull
        Integer headCount,
        @NotNull
        @Size(min = 0)
        Long avgPrice // ??
) {
}

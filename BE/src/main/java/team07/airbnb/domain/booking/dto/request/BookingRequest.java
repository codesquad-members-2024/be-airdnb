package team07.airbnb.domain.booking.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

public record BookingRequest(
        @NotNull
        Long accommodationId,
        @Nullable
        LocalDate checkIn,
        @Nullable
        LocalDate checkOut,
        @Nullable
        Integer headCount,
        @NotNull
        @Size(min = 0)
        Long avgPrice
) {
}

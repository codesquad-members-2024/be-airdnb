package team07.airbnb.data.booking.dto.response;

import java.time.LocalDate;

public record BookingCreateResponse(
        String accName,
        Long bookingId,
        LocalDate checkIn,
        LocalDate checkOut,
        Integer headCount
) {
}

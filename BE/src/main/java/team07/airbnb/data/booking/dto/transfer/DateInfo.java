package team07.airbnb.data.booking.dto.transfer;

import team07.airbnb.data.booking.dto.request.BookingPaymentsRequest;

import java.time.LocalDate;

public record DateInfo(
        LocalDate checkIn,
        LocalDate checkOut
) {
    public static DateInfo of(BookingPaymentsRequest request) {
        return new DateInfo(
                request.checkIn(),
                request.checkOut()
        );
    }
}

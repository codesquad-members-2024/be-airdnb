package team07.airbnb.data.booking.dto.transfer;

import team07.airbnb.data.booking.dto.request.BookingRequest;

import java.time.LocalDate;

public record BookingInfoForPriceInfo(
        LocalDate checkIn,
        LocalDate checkOut,
        Integer headCount,
        Integer avgPrice
) {

    public static BookingInfoForPriceInfo ofRequest(BookingRequest request) {
        return new BookingInfoForPriceInfo(
                request.checkIn(),
                request.checkOut(),
                request.headCount(),
                request.avgPrice()
        );
    }
}

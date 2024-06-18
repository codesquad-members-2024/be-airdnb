package team07.airbnb.data.booking.dto.transfer;

import team07.airbnb.data.booking.dto.request.BookingRequest;

import java.time.LocalDate;

public record BookingInfoForPriceInfo(
        Long accId,
        LocalDate checkIn,
        LocalDate checkOut,
        Integer headCount
) {

    public static BookingInfoForPriceInfo ofRequest(BookingRequest request) {
        return new BookingInfoForPriceInfo(
                request.accommodationId()
,                request.checkIn(),
                request.checkOut(),
                request.headCount()
        );
    }
}

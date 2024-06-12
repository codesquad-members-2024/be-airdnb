package team07.airbnb.data.booking.dto.response;

import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.entity.BookingEntity;

import java.time.LocalDate;

public record BookingManageInfoResponse(
        Long id,
        LocalDate checkIn,
        LocalDate checkOut,
        BookingStatus status
) {

    public static BookingManageInfoResponse of(BookingEntity booking) {
        return new BookingManageInfoResponse(
                booking.getId(),
                booking.getCheckin(),
                booking.getCheckout(),
                booking.getStatus()
        );
    }
}

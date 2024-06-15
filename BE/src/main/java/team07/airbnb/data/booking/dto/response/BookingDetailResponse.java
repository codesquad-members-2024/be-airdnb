package team07.airbnb.data.booking.dto.response;

import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.PaymentEntity;

import java.time.LocalDate;

public record BookingDetailResponse(
        Long id,
        String bookerName,
        LocalDate checkIn,
        LocalDate checkOut,
        Integer headCount,
        BookingStatus status,
        PaymentEntity payment
) {

    public static BookingDetailResponse of(BookingEntity booking) {
        return new BookingDetailResponse(
                booking.getId(),
                booking.getBooker().getName(),
                booking.getCheckin(),
                booking.getCheckout(),
                booking.getHeadCount(),
                booking.getStatus(),
                booking.getPayment()
        );
    }
}

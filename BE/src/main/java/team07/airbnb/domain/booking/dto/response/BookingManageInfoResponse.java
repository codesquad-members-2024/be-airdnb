package team07.airbnb.domain.booking.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team07.airbnb.domain.booking.entity.BookingEntity;
import team07.airbnb.domain.booking.property.BookingStatus;

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

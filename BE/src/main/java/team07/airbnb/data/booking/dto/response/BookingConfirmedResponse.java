package team07.airbnb.data.booking.dto.response;

import team07.airbnb.entity.BookingEntity;

public record BookingConfirmedResponse (
        Long bookingId,
        Long hostId,
        String hostName,
        Long bookerId,
        String bookerName
){

    public static BookingConfirmedResponse of(BookingEntity booking){
        return new BookingConfirmedResponse(
                booking.getId(),
                booking.getHost().getId(),
                booking.getHost().getName(),
                booking.getBooker().getId(),
                booking.getBooker().getName()
        );
    }
}

package team07.airbnb.domain.booking.dto.response;

public record BookingCancelResponse(
        Long bookingId,
        long cancelFee
) {

    public static BookingCancelResponse of(Long bookingId, long cancelFee) {
        return new BookingCancelResponse(bookingId, cancelFee);
    }
}

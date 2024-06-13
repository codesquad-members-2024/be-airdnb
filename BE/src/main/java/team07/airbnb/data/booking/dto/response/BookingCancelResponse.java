package team07.airbnb.data.booking.dto.response;

public record BookingCancelResponse(
        Long bookingId,
        int cancelFee
) {

    public static BookingCancelResponse of(Long bookingId, int cancelFee) {
        return new BookingCancelResponse(bookingId, cancelFee);
    }
}

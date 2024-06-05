package team07.airbnb.domain.booking.dto.response;


public record BookingInfo(
    long roughTotalPrice,
    long discountPrice,
    long serviceFee,
    long accommodationFee
) {

    public static BookingInfo of(long roughTotalPrice, long discountPrice, long serviceFee, long accommodationFee) {
        return new BookingInfo(
                roughTotalPrice,
                discountPrice,
                serviceFee,
                accommodationFee
        );
    }

    public static BookingInfo empty() {
        return new BookingInfo(
                0,0,0,0
        );
    }
}

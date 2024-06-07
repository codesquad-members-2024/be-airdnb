package team07.airbnb.domain.booking.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public class BookingInfo {

    private long roughTotalPrice;
    private long discountPrice;
    private long serviceFee;
    private long accommodationFee;

    public boolean isEmpty() {
        return roughTotalPrice == 0 || discountPrice == 0 || serviceFee == 0 || accommodationFee == 0;
    }

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

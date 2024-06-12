package team07.airbnb.data.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PriceInfo {

    private long roughTotalPrice;
    private long discountPrice;
    private long serviceFee;
    private long accommodationFee;

    public boolean isEmpty() {
        return roughTotalPrice == 0 || discountPrice == 0 || serviceFee == 0 || accommodationFee == 0;
    }

    public static PriceInfo of(long roughTotalPrice, long discountPrice, long serviceFee, long accommodationFee) {
        return new PriceInfo(
                roughTotalPrice,
                discountPrice,
                serviceFee,
                accommodationFee
        );
    }

    public static PriceInfo empty() {
        return new PriceInfo(
                0,0,0,0
        );
    }
}

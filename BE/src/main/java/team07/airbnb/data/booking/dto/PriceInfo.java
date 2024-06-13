package team07.airbnb.data.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PriceInfo {

    private int roughTotalPrice;
    private int discountPrice;
    private int serviceFee;
    private int accommodationFee;

    public boolean isEmpty() {
        return roughTotalPrice == 0 || discountPrice == 0 || serviceFee == 0 || accommodationFee == 0;
    }

    public static PriceInfo of(int roughTotalPrice, int discountPrice, int serviceFee, int accommodationFee) {
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

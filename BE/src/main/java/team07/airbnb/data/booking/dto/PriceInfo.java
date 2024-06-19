package team07.airbnb.data.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PriceInfo {

    private int roughTotalPrice;
    private int discountPrice;
    private int serviceFee;
    private int accommodationFee;

    public static PriceInfo of(int roughTotalPrice, int discountPrice, int serviceFee, int accommodationFee) {
        return new PriceInfo(
                roughTotalPrice,
                discountPrice,
                serviceFee,
                accommodationFee
        );
    }
}

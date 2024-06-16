package team07.airbnb.entity.embed;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.data.booking.dto.PriceInfo;


@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Fee {
    private int serviceFee;
    private int accommodationFee;

    public static Fee of(int serviceFee, int accommodationFee) {
        return new Fee(
                serviceFee,
                accommodationFee
        );
    }
}

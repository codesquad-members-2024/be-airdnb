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
    private long serviceFee;
    private long accommodationFee;

    public static Fee of(PriceInfo priceInfo) {
        return new Fee(
                priceInfo.getServiceFee(),
                priceInfo.getAccommodationFee()
        );
    }
}

package team07.airbnb.domain.booking.price_policy.fee;

import org.springframework.stereotype.Component;
import team07.airbnb.domain.booking.price_policy.RateCalculateUtil;

@Component
public class AccommodationFee {

    private final double feeRate = 10.0;

    public long getAccommodationFeePrice(long serviceFee) {
        return RateCalculateUtil.calculate(feeRate, serviceFee);
    }

}

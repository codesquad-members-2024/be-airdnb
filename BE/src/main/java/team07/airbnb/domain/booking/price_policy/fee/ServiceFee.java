package team07.airbnb.domain.booking.price_policy.fee;

import org.springframework.stereotype.Component;
import team07.airbnb.domain.booking.price_policy.RateCalculateUtil;

@Component
public class ServiceFee {

    private final double feeRate = 15.0;

    public long getFeePrice(long price) {
        return RateCalculateUtil.calculate(feeRate, price);
    }
}

package team07.airbnb.service.booking.price_policy.fee;

import org.springframework.stereotype.Component;
import team07.airbnb.service.booking.price_policy.RateCalculateUtil;

@Component
public class ServiceFee {

    private final double feeRate = 15.0;

    public int getFeePrice(int price) {
        return RateCalculateUtil.calculate(feeRate, price);
    }
}

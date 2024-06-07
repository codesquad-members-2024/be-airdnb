package team07.airbnb.domain.discount.beans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team07.airbnb.domain.booking.price_policy.RateCalculateUtil;

@Component("weeklyRateDiscountPolicy")
@RequiredArgsConstructor
public class WeeklyRateDiscountPolicy implements DiscountPolicy{

    private final double discountRate;

    public WeeklyRateDiscountPolicy() {
        this.discountRate = 4.0;
    }

    @Override
    public long getDiscountPrice(long price) {
        return RateCalculateUtil.calculate(discountRate, price);
    }

}

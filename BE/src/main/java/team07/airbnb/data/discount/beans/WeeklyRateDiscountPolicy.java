package team07.airbnb.data.discount.beans;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import team07.airbnb.service.booking.price_policy.RateCalculateUtil;

@Component("weeklyRateDiscountPolicy")
@RequiredArgsConstructor
public class WeeklyRateDiscountPolicy implements DiscountPolicy {

    private final double discountRate;

    public WeeklyRateDiscountPolicy() {
        this.discountRate = 4.0;
    }

    @Override
    public int getDiscountPrice(int price) {
        return RateCalculateUtil.calculate(discountRate, price);
    }

}

package team07.airbnb.domain.booking.price_policy.discount.beans;


public interface DiscountPolicy {

    long getDiscountPrice(long price);
}

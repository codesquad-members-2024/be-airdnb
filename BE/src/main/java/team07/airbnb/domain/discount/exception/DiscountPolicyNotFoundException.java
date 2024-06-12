package team07.airbnb.domain.discount.exception;

import team07.airbnb.common.exception.NotFoundException;

public class DiscountPolicyNotFoundException extends NotFoundException {
    public DiscountPolicyNotFoundException() {
        super("해당하는 할인 정책이 존재하지 않습니다", "");
    }
}

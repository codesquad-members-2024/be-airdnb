package team07.airbnb.exception.not_found;

import team07.airbnb.exception.not_found.NotFoundException;

public class DiscountPolicyNotFoundException extends NotFoundException {
    public DiscountPolicyNotFoundException() {
        super("해당하는 할인 정책이 존재하지 않습니다", "");
    }
}

package team07.airbnb.exception.not_found;

public class DiscountPolicyNotFoundException extends NotFoundException {
    public DiscountPolicyNotFoundException() {
        super("해당하는 할인 정책이 존재하지 않습니다", "");
    }
}

package team07.airbnb.domain.discount.exception;

public class UnknownDiscountPolicyException extends RuntimeException {
    public UnknownDiscountPolicyException() {
        super("해당하는 할인 정책이 존재하지 않습니다");
    }
}

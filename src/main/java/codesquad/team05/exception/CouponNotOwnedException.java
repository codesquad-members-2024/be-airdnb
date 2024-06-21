package codesquad.team05.exception;

public class CouponNotOwnedException extends RuntimeException {
    public CouponNotOwnedException() {
    }

    public CouponNotOwnedException(String message) {
        super(message);
    }

    public CouponNotOwnedException(String message, Throwable cause) {
        super(message, cause);
    }
}

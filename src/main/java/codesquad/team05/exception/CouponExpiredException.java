package codesquad.team05.exception;

public class CouponExpiredException extends RuntimeException {
    public CouponExpiredException() {
    }

    public CouponExpiredException(String message) {
        super(message);
    }

    public CouponExpiredException(String message, Throwable cause) {
        super(message, cause);
    }
}

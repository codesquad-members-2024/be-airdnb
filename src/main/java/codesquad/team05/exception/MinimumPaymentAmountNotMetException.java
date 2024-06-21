package codesquad.team05.exception;

public class MinimumPaymentAmountNotMetException extends RuntimeException {
    public MinimumPaymentAmountNotMetException() {
    }

    public MinimumPaymentAmountNotMetException(String message) {
        super(message);
    }

    public MinimumPaymentAmountNotMetException(String message, Throwable cause) {
        super(message, cause);
    }
}

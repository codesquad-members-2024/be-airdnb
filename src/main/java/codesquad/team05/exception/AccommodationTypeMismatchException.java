package codesquad.team05.exception;

public class AccommodationTypeMismatchException extends RuntimeException {
    public AccommodationTypeMismatchException() {
    }

    public AccommodationTypeMismatchException(String message) {
        super(message);
    }

    public AccommodationTypeMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}

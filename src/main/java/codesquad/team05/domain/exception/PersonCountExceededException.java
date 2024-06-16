package codesquad.team05.domain.exception;

public class PersonCountExceededException extends RuntimeException {
    public PersonCountExceededException() {
    }

    public PersonCountExceededException(String message) {
        super(message);
    }

    public PersonCountExceededException(String message, Throwable cause) {
        super(message, cause);
    }
}

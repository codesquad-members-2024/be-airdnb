package team07.airbnb.common.exception;

import org.springframework.http.HttpStatus;
import team07.airbnb.common.exception.ApplicationException;

public class NotFoundException extends ApplicationException {
    protected NotFoundException(String message, String log) {
        super(message, HttpStatus.NOT_FOUND, log);
    }
}

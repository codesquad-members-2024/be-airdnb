package team07.airbnb.common.exception.base_exceptions;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {
    protected NotFoundException(String message, String log) {
        super(message, HttpStatus.NOT_FOUND, log);
    }
}

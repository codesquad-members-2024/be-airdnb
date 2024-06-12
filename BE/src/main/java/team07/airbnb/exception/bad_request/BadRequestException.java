package team07.airbnb.exception.bad_request;

import org.springframework.http.HttpStatus;
import team07.airbnb.exception.ApplicationException;

public class BadRequestException extends ApplicationException {
    protected BadRequestException(String message, String log) {
        super(message, HttpStatus.BAD_REQUEST, log);
    }
}

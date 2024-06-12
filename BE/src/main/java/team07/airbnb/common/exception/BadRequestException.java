package team07.airbnb.common.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends ApplicationException{
    protected BadRequestException(String message, String log) {
        super(message, HttpStatus.BAD_REQUEST, log);
    }
}

package team07.airbnb.common.auth.exception;

import org.springframework.http.HttpStatus;
import team07.airbnb.common.exception.ApplicationException;

public class AuthenticateException extends ApplicationException {

    public AuthenticateException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}

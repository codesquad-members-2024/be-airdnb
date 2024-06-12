package team07.airbnb.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApplicationException extends RuntimeException{
    protected HttpStatus status;
    protected String log;

    protected ApplicationException(String message, HttpStatus status, String log) {
        super(message);
        this.status = status;
        this.log = log;
    }
}

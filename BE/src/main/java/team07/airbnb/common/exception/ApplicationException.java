package team07.airbnb.common.exception;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException{
    protected HttpStatus status;

    public HttpStatus getStatus() {
        return status;
    }

    public ApplicationException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}

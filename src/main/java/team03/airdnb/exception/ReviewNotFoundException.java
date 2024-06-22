package team03.airdnb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReviewNotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public ReviewNotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }
}

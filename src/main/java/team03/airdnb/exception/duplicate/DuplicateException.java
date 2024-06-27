package team03.airdnb.exception.duplicate;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import team03.airdnb.exception.ErrorCode;

@Getter
public class DuplicateException extends RuntimeException {

    private final HttpStatus httpStatus;

    public DuplicateException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }

    public DuplicateException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.httpStatus = errorCode.getHttpStatus();
    }
}
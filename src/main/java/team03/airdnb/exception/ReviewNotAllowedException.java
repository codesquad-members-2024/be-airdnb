package team03.airdnb.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ReviewNotAllowedException extends RuntimeException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.REVIEW_NOT_ALLOWED;

    private final HttpStatus httpStatus;

    public ReviewNotAllowedException() {
        super(DEFAULT_ERROR_CODE.getMessage());
        this.httpStatus = DEFAULT_ERROR_CODE.getHttpStatus();
    }
}

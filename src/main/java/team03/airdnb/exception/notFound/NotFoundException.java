package team03.airdnb.exception.notFound;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import team03.airdnb.exception.ErrorCode;

@Getter
public class NotFoundException extends RuntimeException {

    private final HttpStatus httpStatus;

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.httpStatus = errorCode.getHttpStatus();
    }

    public NotFoundException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.httpStatus = errorCode.getHttpStatus();
    }
}

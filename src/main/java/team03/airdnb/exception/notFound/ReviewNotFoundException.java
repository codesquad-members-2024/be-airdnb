package team03.airdnb.exception.notFound;

import lombok.Getter;
import team03.airdnb.exception.ErrorCode;

@Getter
public class ReviewNotFoundException extends NotFoundException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.REVIEW_NOT_FOUND;

    public ReviewNotFoundException() {
        super(DEFAULT_ERROR_CODE);
    }

    public ReviewNotFoundException(String customMessage) {
        super(DEFAULT_ERROR_CODE, customMessage);
    }
}
package team03.airdnb.exception.notFound;

import lombok.Getter;
import team03.airdnb.exception.ErrorCode;

@Getter
public class UserNotFoundException extends NotFoundException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.USER_NOT_FOUND;

    public UserNotFoundException() {
        super(DEFAULT_ERROR_CODE);
    }

    public UserNotFoundException(String customMessage) {
        super(DEFAULT_ERROR_CODE, customMessage);
    }
}
package team03.airdnb.exception.duplicate;

import lombok.Getter;
import team03.airdnb.exception.ErrorCode;

@Getter
public class DuplicateNameException extends DuplicateException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.DUPLICATE_NAME;

    public DuplicateNameException() {
        super(DEFAULT_ERROR_CODE);
    }

    public DuplicateNameException(String customMessage) {
        super(DEFAULT_ERROR_CODE, customMessage);
    }
}
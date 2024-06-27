package team03.airdnb.exception.duplicate;

import lombok.Getter;
import team03.airdnb.exception.ErrorCode;

@Getter
public class DuplicateReservationException extends DuplicateException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.DUPLICATE_NAME;

    public DuplicateReservationException() {
        super(DEFAULT_ERROR_CODE);
    }

    public DuplicateReservationException(String customMessage) {
        super(DEFAULT_ERROR_CODE, customMessage);
    }
}

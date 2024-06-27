package team03.airdnb.exception.notFound;

import lombok.Getter;
import team03.airdnb.exception.ErrorCode;

@Getter
public class AddressNotFoundException extends NotFoundException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.ADDRESS_NOT_FOUND;

    public AddressNotFoundException() {
        super(DEFAULT_ERROR_CODE);
    }

    public AddressNotFoundException(String customMessage) {
        super(DEFAULT_ERROR_CODE, customMessage);
    }
}
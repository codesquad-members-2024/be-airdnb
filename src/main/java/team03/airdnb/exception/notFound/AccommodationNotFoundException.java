package team03.airdnb.exception.notFound;

import lombok.Getter;
import team03.airdnb.exception.ErrorCode;

@Getter
public class AccommodationNotFoundException extends NotFoundException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.ACCOMMODATION_NOT_FOUND;

    public AccommodationNotFoundException() {
        super(DEFAULT_ERROR_CODE);
    }

    public AccommodationNotFoundException(String customMessage) {
        super(DEFAULT_ERROR_CODE, customMessage);
    }
}
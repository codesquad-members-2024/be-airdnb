package team03.airdnb.exception.notFound;

import lombok.Getter;
import team03.airdnb.exception.ErrorCode;

@Getter
public class AmenityNotFoundException extends NotFoundException {

    private static final ErrorCode DEFAULT_ERROR_CODE = ErrorCode.AMENITY_NOT_FOUND;

    public AmenityNotFoundException() {
        super(DEFAULT_ERROR_CODE);
    }

    public AmenityNotFoundException(String customMessage) {
        super(DEFAULT_ERROR_CODE, customMessage);
    }
}
package team10.airdnb.amenity.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AmenityIdNotFoundException extends BusinessException {
    public AmenityIdNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

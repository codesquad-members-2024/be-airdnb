package team10.airdnb.amenity.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AmenityNameDuplicateException extends BusinessException {
    public AmenityNameDuplicateException() { super(ErrorCode.ALREADY_SAVED_AMENITY);}
}

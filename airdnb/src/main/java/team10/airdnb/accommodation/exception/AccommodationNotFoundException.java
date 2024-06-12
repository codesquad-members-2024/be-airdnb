package team10.airdnb.accommodation.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AccommodationNotFoundException extends BusinessException {
    public AccommodationNotFoundException() {
        super(ErrorCode.ACCOMMODATION_NOT_EXISTS);
    }
}

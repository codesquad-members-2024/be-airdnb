package team10.airdnb.accommodation.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AccommodationIdNotFoundException extends BusinessException {
    public AccommodationIdNotFoundException() {
        super(ErrorCode.ACCOMMODATION_NOT_EXISTS);
    }

}

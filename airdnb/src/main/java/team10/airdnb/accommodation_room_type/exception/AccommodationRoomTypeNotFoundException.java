package team10.airdnb.accommodation_room_type.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AccommodationRoomTypeNotFoundException extends BusinessException {
    public AccommodationRoomTypeNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}

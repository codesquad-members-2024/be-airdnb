package team10.airdnb.accommodation_room_type.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AccommodationRoomTypeNameDuplicateException extends BusinessException {
    public AccommodationRoomTypeNameDuplicateException() {
        super(ErrorCode.ALREADY_SAVED_ROOM_TYPE);
    }
}

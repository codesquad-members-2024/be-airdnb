package team10.airdnb.reservation.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class ReservationIdNotFoundException extends BusinessException {
    public ReservationIdNotFoundException() {
        super(ErrorCode.RESERVATION_NOT_EXISTS);
    }
}

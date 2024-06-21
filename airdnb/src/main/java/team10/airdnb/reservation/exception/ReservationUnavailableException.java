package team10.airdnb.reservation.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class ReservationUnavailableException extends BusinessException {
    public ReservationUnavailableException() {
        super(ErrorCode.RESERVATION_NOT_AVAILABLE);
    }
}

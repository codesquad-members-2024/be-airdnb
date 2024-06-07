package com.airdnb.reservation;

import com.airdnb.global.BizException;
import org.springframework.http.HttpStatus;

public class InvalidReservationException extends BizException {
    public InvalidReservationException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public InvalidReservationException(String message, Throwable cause) {
        super(message, HttpStatus.BAD_REQUEST, cause);
    }
}

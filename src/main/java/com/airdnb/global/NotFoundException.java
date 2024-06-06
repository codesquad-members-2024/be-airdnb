package com.airdnb.global;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BizException {
    public NotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }
}

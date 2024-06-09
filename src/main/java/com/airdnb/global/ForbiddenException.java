package com.airdnb.global;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BizException {
    public ForbiddenException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, HttpStatus.FORBIDDEN, cause);
    }
}

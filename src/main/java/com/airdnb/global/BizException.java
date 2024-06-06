package com.airdnb.global;

import org.springframework.http.HttpStatus;

public class BizException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BizException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}

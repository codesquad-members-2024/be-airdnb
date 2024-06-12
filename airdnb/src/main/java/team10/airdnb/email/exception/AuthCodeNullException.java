package team10.airdnb.email.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AuthCodeNullException extends BusinessException {
    public AuthCodeNullException() {
        super(ErrorCode.AUTH_CODE_NOT_EXISTS);
    }
}

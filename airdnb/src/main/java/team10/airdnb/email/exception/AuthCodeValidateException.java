package team10.airdnb.email.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class AuthCodeValidateException extends BusinessException {
    public AuthCodeValidateException(ErrorCode errorCode) {
        super(errorCode);
    }
}

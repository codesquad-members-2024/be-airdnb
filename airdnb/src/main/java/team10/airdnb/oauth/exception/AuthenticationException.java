package team10.airdnb.oauth.exception;


import team10.airdnb.exception.BusinessException;
import team10.airdnb.error.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

}

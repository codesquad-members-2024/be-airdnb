package team10.airdnb.oauth.error.exception;


import team10.airdnb.oauth.error.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }

}

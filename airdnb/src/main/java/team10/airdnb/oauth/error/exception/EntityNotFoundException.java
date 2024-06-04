package team10.airdnb.oauth.error.exception;

import team10.airdnb.oauth.error.ErrorCode;

public class EntityNotFoundException extends BusinessException {

    public EntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}


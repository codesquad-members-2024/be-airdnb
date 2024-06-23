package team10.airdnb.member.exception;

import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

public class MemberLoginException extends BusinessException {
    public MemberLoginException() {
        super(ErrorCode.MEMBER_PASSWORD_INVALID);
    }
}

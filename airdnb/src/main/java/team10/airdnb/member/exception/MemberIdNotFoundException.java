package team10.airdnb.member.exception;

import team10.airdnb.exception.BusinessException;
import team10.airdnb.error.ErrorCode;

public class MemberIdNotFoundException extends BusinessException {
    public MemberIdNotFoundException() {
        super(ErrorCode.MEMBER_NOT_EXISTS);
    }
}

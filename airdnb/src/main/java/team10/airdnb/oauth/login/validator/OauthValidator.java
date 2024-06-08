package team10.airdnb.oauth.login.validator;

import org.springframework.stereotype.Service;
import team10.airdnb.member.constant.MemberType;
import team10.airdnb.error.ErrorCode;
import team10.airdnb.exception.BusinessException;

@Service
public class OauthValidator {

    public void validateMemberType(String memberType) {
        if(!MemberType.isMemberType(memberType)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }

}

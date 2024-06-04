package team10.airdnb.oauth.login.validator;

import org.springframework.stereotype.Service;
import team10.airdnb.Member.constant.MemberType;
import team10.airdnb.oauth.error.ErrorCode;
import team10.airdnb.oauth.error.exception.BusinessException;

@Service
public class OauthValidator {

    public void validateMemberType(String memberType) {
        if(!MemberType.isMemberType(memberType)) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        }
    }

}

package team10.airdnb.oauth.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import team10.airdnb.Member.constant.MemberType;
import team10.airdnb.Member.constant.Role;
import team10.airdnb.Member.entity.Member;

@ToString
@Getter
@Builder
public class OAuthAttributes { // 회원 정보 가져올 때 통일

    private String name;
    private String email;
    private String profile;
    private MemberType memberType;

    public Member toMemberEntity(MemberType memberType) {
        return Member.builder()
                .id(email)
                .memberName(name)
                .email(email)
                .memberType(memberType)
                .profile(profile)
                .build();
    }

}

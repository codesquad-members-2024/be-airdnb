package codesquad.airdnb.domain.member.oauth.kakao;

import codesquad.airdnb.domain.member.oauth.OAuthProvider;
import codesquad.airdnb.domain.member.oauth.OAuthUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class KaKaoUserInfo implements OAuthUserInfo {

    private Long id;

    private kakaoAccount kakaoAccount;

    // 필요한 정보인 email만 가져오기 위함
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class kakaoAccount {
        String email;

        Profile profile;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        static class Profile {
            String nickname;

            String profileImageUrl;
        }
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public String getEmail() {
        return kakaoAccount.email;
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public String getNickname() {
        return kakaoAccount.profile.nickname;
    }

    @Override
    public String getProfileImageUrl() {
        return kakaoAccount.profile.profileImageUrl;
    }
}

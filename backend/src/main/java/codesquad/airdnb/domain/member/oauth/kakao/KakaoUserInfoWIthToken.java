package codesquad.airdnb.domain.member.oauth.kakao;

import codesquad.airdnb.domain.member.oauth.OAuthProvider;
import codesquad.airdnb.domain.member.oauth.OAuthToken;
import codesquad.airdnb.domain.member.oauth.OAuthUserInfo;
import codesquad.airdnb.domain.member.oauth.OAuthUserInfoWithToken;

public class KakaoUserInfoWIthToken implements OAuthUserInfoWithToken {
    private final OAuthUserInfo userInfo;
    private final String accessToken;
    private final String refreshToken;

    public KakaoUserInfoWIthToken(OAuthUserInfo userInfo, OAuthToken oAuthToken) {
        this.userInfo = userInfo;
        this.accessToken = oAuthToken.getAccessToken();
        this.refreshToken = oAuthToken.getRefreshToken();
    }

    @Override
    public Long getId() {
        return userInfo.getId();
    }

    @Override
    public String getEmail() {
        return userInfo.getEmail();
    }

    @Override
    public OAuthProvider getOAuthProvider() {
        return userInfo.getOAuthProvider();
    }

    @Override
    public String getProfileImageUrl() {
        return userInfo.getProfileImageUrl();
    }

    @Override
    public String getNickname() {
        return userInfo.getNickname();
    }

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }


}

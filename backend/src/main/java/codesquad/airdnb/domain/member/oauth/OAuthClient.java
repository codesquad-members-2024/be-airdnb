package codesquad.airdnb.domain.member.oauth;

import org.springframework.http.ResponseEntity;

public interface OAuthClient {
    OAuthProvider oAuthProvider();

    OAuthToken requestAccessToken(OAuthLoginParams params);

    // 이미 발급받은 accessToken으로 사용자의 정보를 반환받기 위한 메서드
    OAuthUserInfo requestOAuthInfo(String accessToken);

    // 사용자가 로그인 시 사용자의 정보와 accessToken, RefreshToken까지 반환받는 데에 사용하는 메서드
    OAuthUserInfoWithToken requestOAuthInfoWithToken(OAuthLoginParams params);

    ResponseEntity<String> requestExpireOAuthTokens(String oauthAccessToken);
}

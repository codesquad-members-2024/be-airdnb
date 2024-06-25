package codesquad.airdnb.domain.member.oauth;

// OAuth를 통한 로그인 시 토큰들까지 받아 Oauth 테이블에 저장하기 위해 사용되는 인터페이스입니다.
public interface OAuthUserInfoWithToken extends OAuthUserInfo {
    String getAccessToken();
    String getRefreshToken();
}

package codesquad.airdnb.domain.member.oauth;

// 사용자 정보를 요청할 때 사용할 인터페이스. (OAuth 토큰들은 받아오지 않습니다.)
public interface OAuthUserInfo {

    Long getId();

    String getEmail();

    OAuthProvider getOAuthProvider();

    String getProfileImageUrl();

    String getNickname();
}

package codesquad.airdnb.domain.member.oauth.kakao;

import codesquad.airdnb.domain.member.oauth.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class KakaoOAuthClient implements OAuthClient {

    @Override
    public OAuthProvider oAuthProvider() {
        return OAuthProvider.KAKAO;
    }

    @Override
    public OAuthToken requestAccessToken(OAuthLoginParams params) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> request = generateHttpRequest(params);

        KakaoToken kakaoToken = restTemplate.exchange(KakaoConstants.TOKEN_URL, HttpMethod.POST, request, KakaoToken.class).getBody();

        Objects.requireNonNull(kakaoToken);
        return OAuthToken.builder().
                accessToken(kakaoToken.accessToken())
                .refreshToken(kakaoToken.refreshToken())
                .build();
    }

    @Override
    public OAuthUserInfo requestOAuthInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setBearerAuth(accessToken);

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        // kakao_account.profile 하위에 nickname, profileImageUrl가 존재한다.
        body.add("property_keys", "[\"kakao_account.email\", \"kakao_account.profile\"]");

        HttpEntity<?> request = new HttpEntity<>(body, httpHeaders);
        return restTemplate.exchange(KakaoConstants.CODE_URL, HttpMethod.POST, request, KaKaoUserInfo.class).getBody();
    }

    @Override
    public OAuthUserInfoWithToken requestOAuthInfoWithToken(OAuthLoginParams params) {
        OAuthToken oAuthToken = requestAccessToken(params);
        OAuthUserInfo userInfo = requestOAuthInfo(oAuthToken.getAccessToken());
        return new KakaoUserInfoWIthToken(userInfo, oAuthToken);
    }

    @Override
    public ResponseEntity<String> requestExpireOAuthTokens(String oauthAccessToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBearerAuth(oauthAccessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(KakaoConstants.LOGOUT_URL, HttpMethod.POST, entity, String.class);
    }

    private HttpEntity<MultiValueMap<String, String>> generateHttpRequest(OAuthLoginParams params) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // authorization_code는 params.makeBody() 에서 만들어 온다.
        MultiValueMap<String, String> body = params.makeBody();
        body.add("grant_type", "authorization_code");
        body.add("client_id", KakaoConstants.CLIENT_ID);
        body.add("client_secret", KakaoConstants.CLIENT_SECRET);
        body.add("redirect_uri", KakaoConstants.REDIRECT_URL);
        return new HttpEntity<>(body, httpHeaders);
    }
}

package codesquad.airdnb.domain.member.oauth;

import codesquad.airdnb.domain.member.OAuthRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RequestOAuthInfoService {

    private final Map<OAuthProvider, OAuthClient> clients;

    public RequestOAuthInfoService(List<OAuthClient> clients) {
        this.clients = clients.stream().collect(
                Collectors.toUnmodifiableMap(OAuthClient::oAuthProvider, Function.identity())
        );
    }

    public OAuthUserInfo request(OAuthLoginParams params) {
        OAuthClient client = clients.get(params.oAuthprovider());
        OAuthToken oAuthToken = client.requestAccessToken(params);
        return client.requestOAuthInfo(oAuthToken.getAccessToken());
    }

    public OAuthUserInfoWithToken requestWithToken(OAuthLoginParams params) {
        OAuthClient client = clients.get(params.oAuthprovider());
        return client.requestOAuthInfoWithToken(params);
    }

    public ResponseEntity<String> requestExpireOAuthTokens(String oauthAccessToken, OAuthProvider oAuthProvider) {
        OAuthClient client = clients.get(oAuthProvider);
        return client.requestExpireOAuthTokens(oauthAccessToken);
    }
}

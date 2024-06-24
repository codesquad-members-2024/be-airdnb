package codesquad.airdnb.domain.member.oauth.kakao;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class KakaoConstants {

    private String tokenUrl;
    private String codeUrl;
    private String logoutUrl;
    private String redirectUrl;
    private String clientId;
    private String clientSecret;
    private String authUrl;

    public static String TOKEN_URL;
    public static String CODE_URL;
    public static String LOGOUT_URL;
    public static String REDIRECT_URL;
    public static String CLIENT_ID;
    public static String CLIENT_SECRET;
    public static String AUTH_URL;

    @Value("${oauth.kakao.url.token}")
    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    @Value("${oauth.kakao.url.code}")
    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    @Value("${oauth.kakao.url.logout}")
    public void setLogoutUrl(String logoutUrl) {
        this.logoutUrl = logoutUrl;
    }

    @Value("${oauth.kakao.url.redirect}")
    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    @Value("${oauth.kakao.client-id}")
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Value("${oauth.kakao.client-secret}")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Value("${oauth.kakao.url.auth}")
    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }

    @PostConstruct
    private void init() {
        TOKEN_URL = this.tokenUrl;
        CODE_URL = this.codeUrl;
        LOGOUT_URL = this.logoutUrl;
        REDIRECT_URL = this.redirectUrl;
        CLIENT_ID = this.clientId;
        CLIENT_SECRET = this.clientSecret;
        AUTH_URL = this.authUrl;
    }
}

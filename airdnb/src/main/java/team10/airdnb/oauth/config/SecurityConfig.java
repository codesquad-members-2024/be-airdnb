package team10.airdnb.oauth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team10.airdnb.oauth.jwt.service.TokenManager;

//jwt yml 설정 파일 읽기
@Configuration
public class SecurityConfig {

    @Value("${token.access-token-expiration-time}")
    private String accessTokenExpirationTime;

    @Value("${token.refresh-token-expiration-time}")
    private String refreshTokenExpirationTime;

    @Value("${token.secret}")
    private String tokenSecret;

    @Bean
    public TokenManager tokenManager() {
        return new TokenManager(accessTokenExpirationTime, refreshTokenExpirationTime, tokenSecret);
    }

}


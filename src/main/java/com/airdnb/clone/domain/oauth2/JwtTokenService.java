package com.airdnb.clone.domain.oauth2;

import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_EXPIRATION_TIME;
import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_ISSUER;
import static com.airdnb.clone.global.security.constants.SecurityConstants.JWT_SUBJECT;
import static com.airdnb.clone.global.security.constants.SecurityConstants.REFRESH_JWT_EXPIRATION_TIME;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.crypto.SecretKey;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

@Getter
@Service
public class JwtTokenService {

    @Value("#{environment['jwt.secret']}")
    private String JWT_SECRET;

    public String issueAccessToken(Map<String, Object> claims) {
        return issueToken(claims, JWT_EXPIRATION_TIME);
    }

    public String issueRefreshToken(Map<String, Object> claims) {
        return issueToken(claims, REFRESH_JWT_EXPIRATION_TIME);
    }

    private String issueToken(Map<String, Object> claims, int expirationTime) {
        return Jwts.builder()
                .issuer(JWT_ISSUER)
                .subject(JWT_SUBJECT)
                .claims(claims)
                .issuedAt(Timestamp.valueOf(LocalDateTime.now()))
                .expiration(Timestamp.valueOf(LocalDateTime.now().plusMinutes(expirationTime)))
                .signWith(getSecretKey())
                .compact();
    }

    public Claims getClaims(String jwt) throws Exception {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(jwt) // jwt 의 signClaim 부분을 파싱
                .getPayload(); // 페이로드 부분 읽기
    }

    public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authorities = new HashSet<>();

        for (GrantedAuthority authority : collection) {
            authorities.add(authority.getAuthority());
        }

        return String.join(",", authorities);
    }

    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
    }
}

package com.airdnb.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String signatureSecretKey;

    private SecretKey key;

    @PostConstruct
    protected void init() {
        this.key = Keys.hmacShaKeyFor(signatureSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    @Value("${jwt.token-valid-time}")
    private Long tokenValidTime;

    public String createToken(String id) {
        return Jwts.builder()
            .claim("id", id)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + tokenValidTime))
            .signWith(key)
            .compact();

    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token;
    }

    public Boolean validateToken(String token) {
        try {
            log.info("token: {}", token);
            log.info("secretKey: {}", key);
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SignatureException e) {
            log.info("유효하지않은 서명");
        } catch (MalformedJwtException e) {
            log.info("JWT 형식 오류");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰의 문자열 이상");
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT");
        }
        return false;
    }

    public String getId(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().get("id", String.class);
    }
}

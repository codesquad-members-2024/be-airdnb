package codesquad.airdnb.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final long MINUTE = Duration.ofMinutes(1).toMillis();
    private static final long DAY = Duration.ofDays(1).toMillis();
    private static final long MONTH = Duration.ofDays(30).toMillis();

    public static final long ACCESS_TOKEN_EXP_TIME = 30 * MINUTE;
    public static final long REFRESH_TOKEN_EXP_TIME = 3 * MONTH;

    public static final long TOKEN_REFRESH_DURATION = 30;

    @Value("${jwt.secret-key}")
    private String secretKey;
    private SecretKey jwtSecretKey;

    public static final String TOKEN_HEADER_PREFIX = "Bearer ";

    @PostConstruct
    protected void init() {
        this.jwtSecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String getToken(String authorizationHeaderValue) {
        if (!authorizationHeaderValue.startsWith(TOKEN_HEADER_PREFIX)) {
            return null;
        }
        return authorizationHeaderValue.substring(TOKEN_HEADER_PREFIX.length());
    }

    public String createAccessToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP_TIME))
                .signWith(jwtSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String createAccessToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXP_TIME))
                .signWith(jwtSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(String subject) {
        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP_TIME))
                .signWith(jwtSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String createRefreshToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .subject(subject)
                .claims(claims)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXP_TIME))
                .signWith(jwtSecretKey, Jwts.SIG.HS256)
                .compact();
    }

    public String getClaimFromToken(String token, String keyString) {
        Claims claims = Jwts.parser()
                .verifyWith(jwtSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.get(keyString, String.class);
    }


    public Claims validateToken(String token) throws SignatureException {
        if (token == null) {
            throw new IllegalStateException();
        }

        try {
            return Jwts.parser()
                    .verifyWith(jwtSecretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new SignatureException("잘못된 토큰입니다");
        }
    }

    public String getSubjectFromAuthHeader(String authHeader) {
        String token = getToken(authHeader);
        Claims claims = validateToken(token);
        return claims.getSubject();
    }

    private Long calculateRefreshTokenExpiredDays(Claims claims) {
        long expTime = claims.getExpiration().getTime() * 1000;
        return (expTime - System.currentTimeMillis()) / DAY;
    }

    public boolean refreshTokenExpired(Claims claims) {
        Long expTime = calculateRefreshTokenExpiredDays(claims);
        return expTime < TOKEN_REFRESH_DURATION;
    }

}
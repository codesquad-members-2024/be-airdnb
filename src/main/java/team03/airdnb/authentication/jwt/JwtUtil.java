package team03.airdnb.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final int EXPIRATION_TIME = 10 * 60 * 60 * 1000; // 10시간
    private static final String AUTHORIZATION_HEADER_START = "Bearer ";

    @Value("${jwt.secret-key}")
    private String secretKeyString;
    private SecretKey SECRET_KEY;

    @PostConstruct
    public void init() {
        SECRET_KEY = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    public String extractUsername(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        final Claims claims = extractAllClaims(token);
        return claims.getExpiration();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getJwtTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith(AUTHORIZATION_HEADER_START)) {
            return authorizationHeader.substring(7);
        }
        return null;
    }
}

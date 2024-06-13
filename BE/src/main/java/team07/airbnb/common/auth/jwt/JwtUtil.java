package team07.airbnb.common.auth.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team07.airbnb.data.user.dto.response.TokenUserInfo;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {

    private long jwtExpirationMs;

    @Autowired
    private ObjectMapper mapper;

    private final SecretKey key;

    public JwtUtil(
            @Value("${jwt.secret}") String jwtSecret,
            @Value("${jwt.expiration_time}") long jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    public String generateToken(JwtUserDetails userDetails) throws JsonProcessingException {
        TokenUserInfo user = userDetails.getUser();
        String userJson = mapper.writeValueAsString(user);

        return Jwts.builder()
                .claim("user", userJson)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("Not Valid Token %s".formatted(token));
        }
        return false;
    }

    public TokenUserInfo getTokenUserInfo(String token) throws JsonProcessingException {
        Claims claims = extractClaims(token);
        String userJson = claims.get("user", String.class);
        return mapper.readValue(userJson, TokenUserInfo.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}

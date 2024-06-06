package team07.airbnb.util.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.util.JwtUserDetails;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JwtUtil {

    private long jwtExpirationMs;

    @Autowired
    private ObjectMapper mapper;

    private final SecretKey key;

    public JwtUtil(
            @Value("${jwt.secret}")String jwtSecret,
            @Value("${jwt.expiration_time}") long jwtExpirationMs) {
        this.jwtExpirationMs = jwtExpirationMs;
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    public String generateToken(JwtUserDetails userDetails) throws JsonProcessingException {
        UserEntity user = userDetails.getUser();
        String userJson = mapper.writeValueAsString(user);

        return Jwts.builder()
                .subject(user.getEmail())
                .claim("user", userJson)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public UserEntity getUserEntity(String token) throws JsonProcessingException {
        Claims claims = extractClaims(token);

        String userJson = claims.get("user", String.class);
        log.info(userJson);

        return mapper.readValue(userJson, UserEntity.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // Log the exception or handle it as needed
        }
        return false;
    }
}

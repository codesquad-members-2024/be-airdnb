package com.airdnb.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private Key key;

    @Value("${jwt.token-valid-time}")
    private Long tokenValidTime;

    @PostConstruct
    protected void init() {
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(String id) {
        return Jwts.builder()
            .subject(id)
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + tokenValidTime))
            .signWith(key)
            .compact();

    }

    public String getToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token;
    }
}

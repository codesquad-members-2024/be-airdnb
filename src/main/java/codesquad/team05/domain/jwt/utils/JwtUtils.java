package codesquad.team05.domain.jwt.utils;

import codesquad.team05.domain.jwt.constants.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;


public class JwtUtils {

    private final static SecretKey key = Keys.hmacShaKeyFor(JwtConstants.SECRET_KEY.getBytes());

    public static String generateAccessToken(String userId) {
        return Jwts.builder()
                .subject(JwtConstants.ACCESS)
                .header()
                .add("typ", "JWT")
                .add("alg", "HS512")
                .and()
                .claim("id", userId)
                .expiration(createExpireDate(JwtConstants.ACCESS_EXP_TIME))
                .signWith(key)
                .compact();

    }

    public static String generateRefreshToken() {
        return UUID.randomUUID().toString();

    }

    private static Date createExpireDate(long expireTime) {
        return new Date(System.currentTimeMillis() + expireTime);
    }

    public static String getTokenFromHeader(String header) {
        return header.split(" ")[1];
    }

    public static String resolveToken(HttpServletRequest request, String tokenType) {
        return request.getHeader(tokenType);
    }

    public static void setTokenSetHeader(String accessToken, HttpServletResponse response) {
        response.setHeader("Authorization", "Bearer " + accessToken);
    }

    //토큰 유효성 검사
    public static Claims validateJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token)
                .getPayload();

    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build().parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public static String getUsernameFromToken(String token) {
        Claims claims =             Jwts.parser()
                .verifyWith(key)
                .build().parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }

    public static UsernamePasswordAuthenticationToken getAuthenticationToken(Claims claims) {

        String id = claims.get("id", String.class);

        return new UsernamePasswordAuthenticationToken(id, null, null);
    }


}

package com.team01.airdnb.authentication.jwt;

import com.team01.airdnb.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class TokenProvider {
    @Value("${jwt.secret}")
    private String KEY;
    private SecretKey secretKey;
    @Value("${jwt.expiration_time}")
    private long ACCESS_TOKEN_EXPIRE_TIME;
    private static final String KEY_ROLE = "role";
    //private final TokenService tokenService;

    @PostConstruct
    private void setSecretKey() {
        secretKey = Keys.hmacShaKeyFor(KEY.getBytes());
    }

    public String generateAccessToken(Authentication authentication) {
        return generateToken(authentication, ACCESS_TOKEN_EXPIRE_TIME);
    }

//    // 1. refresh token 발급
//    public void generateRefreshToken(Authentication authentication, String accessToken) {
//        String refreshToken = generateToken(authentication, REFRESH_TOKEN_EXPIRE_TIME);
//        tokenService.saveOrUpdate(authentication.getName(), refreshToken, accessToken); // redis에 저장
//    }

    private String generateToken(Authentication authentication, long expireTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        String email = ((UserDetails) authentication.getPrincipal()).getUsername(); // email을 username으로 사용한다고 가정
        return Jwts.builder()
                .claim(KEY_ROLE, authorities)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

//    public Authentication getAuthentication(String token) {
//        Claims claims = parseClaims(token);
//        List<SimpleGrantedAuthority> authorities = getAuthorities(claims);
//
//        // 2. security의 User 객체 생성
//        User principal = new User(claims.getSubject(), "", authorities);
//        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
//    }
//
//    private List<SimpleGrantedAuthority> getAuthorities(Claims claims) {
//        return Collections.singletonList(new SimpleGrantedAuthority(
//                claims.get(KEY_ROLE).toString()));
//    }
//
//    // 3. accessToken 재발급
//    public String reissueAccessToken(String accessToken) {
//        if (StringUtils.hasText(accessToken)) {
//            Token token = tokenService.findByAccessTokenOrThrow(accessToken);
//            String refreshToken = token.getRefreshToken();
//
//            if (validateToken(refreshToken)) {
//                String reissueAccessToken = generateAccessToken(getAuthentication(refreshToken));
//                tokenService.updateToken(reissueAccessToken, token);
//                return reissueAccessToken;
//            }
//        }
//        return null;
//    }

//    public boolean validateToken(String token) {
//        if (!StringUtils.hasText(token)) {
//            return false;
//        }
//
//        Claims claims = parseClaims(token);
//        return claims.getExpiration().after(new Date());
//    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        } catch (Exception e) {
            // 필요한 경우 다른 예외 처리 추가 가능
            throw new RuntimeException("Invalid JWT token", e);
        }
//        } catch (MalformedJwtException e) {
//            throw new TokenException(INVALID_TOKEN);
//        } catch (SecurityException e) {
//            throw new TokenException(INVALID_JWT_SIGNATURE);
//        }
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey)
                .build().parseClaimsJws(token);

            return claims.getBody().getExpiration()
                .after(new Date(System.currentTimeMillis()));

        } catch (Exception e) {
            return false;
        }
    }

    public String getEmail(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build().parseClaimsJws(token).getBody().getSubject();
    }
}
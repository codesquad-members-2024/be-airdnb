package codesquad.team05.domain.jwt.service;

import codesquad.team05.domain.jwt.repository.JwtRepository;
import codesquad.team05.domain.jwt.token.RefreshToken;
import codesquad.team05.domain.jwt.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JwtService {

    private final JwtRepository jwtRepository;

    public String generateRefreshToken(String userId){
        String refreshToken = JwtUtils.generateRefreshToken();
        jwtRepository.save(new RefreshToken(userId, refreshToken));
        return refreshToken;
    }

    public String generateAccessToken(String userId){
        return JwtUtils.generateAccessToken(userId);
    }

    public Optional<RefreshToken> findByToken(String loginId){
        return jwtRepository.findByToken(loginId);
    }

    public String renewToken(String accessToken, String refreshToken){
        //token이 존재하는지 찾고, 존재하면 RefreshToken 안에 있는 memberId를 가져온다.
        //이를 통해 member를 찾은 후 AccessToken 생성

        RefreshToken token = this.findByToken(refreshToken).orElseThrow(NoSuchElementException::new);
        Claims claims = JwtUtils.validateJwtToken(token.getToken());

        String loginId = claims.getSubject();
        RefreshToken refresh = this.findByToken(loginId).orElseThrow();

        if(jwtRepository.isTokenValid(loginId) && refresh.getToken().equals(refreshToken)){
            Long expiration = getExpiration(accessToken);
            jwtRepository.setBlackList(accessToken, "access_token", expiration);


            return this.generateAccessToken(loginId);
        } else {

            if (!refresh.getToken().equals(refreshToken)){

                //refreshtoken이 해킹된 상황에 경우 request에서 보내는 refresh token과 redis에 저장된 refres token이 다를 수 있다.
                //해커가 refresh token을 새로 발급받은 상황일 수 있기 때문이다.

                //이에 이렇게 불일치가 되는 경우에는 해킹의 흔적으로 보고 redis에 넣은 refresh token을 삭제하고 access token을 무효화한다.
                jwtRepository.deleteByUserId(loginId);
            }
            throw new NoSuchElementException();
        }
   }

   public void deleteRefreshToken(String userId){
        jwtRepository.deleteByUserId(userId);
   }

   public Long getExpiration(String accessToken){
       Claims claims = JwtUtils.validateJwtToken(accessToken);
       Date expiration = claims.getExpiration();
       return expiration.getTime()-new Date().getTime();
   }
}

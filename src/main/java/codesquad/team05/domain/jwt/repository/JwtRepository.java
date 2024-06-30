package codesquad.team05.domain.jwt.repository;

import codesquad.team05.domain.jwt.constants.JwtConstants;
import codesquad.team05.domain.jwt.token.RefreshToken;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Repository
@RequiredArgsConstructor
public class JwtRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public RefreshToken save(RefreshToken refreshToken){
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(refreshToken.getUserId(), refreshToken.getToken());
        redisTemplate.expire(refreshToken.getUserId(), JwtConstants.REFRESH_EXP_TIME, TimeUnit.MILLISECONDS);

        return refreshToken;
    }

    public Optional<RefreshToken> findByToken(String userId){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String refreshToken = valueOperations.get(userId);

        if(Objects.isNull(refreshToken)){
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(userId, refreshToken));
    }

    public void deleteByUserId(String userId){
        redisTemplate.delete(userId);
    }

    public boolean isTokenValid(String userId){
        return redisTemplate.hasKey(userId);
    }

    public void setBlackList(String key, String object, Long millSeconds){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, object, millSeconds, TimeUnit.MILLISECONDS);
    }

    public void  deleteBlackList(String accessToken){
        redisTemplate.delete(accessToken);
    }

    public void hasBlackList(String accessToken){
        redisTemplate.hasKey(accessToken);
    }
}

package codesquad.team05.domain.coupon;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponCountRepository {

    private final RedisTemplate<String, String> redisTemplate;

    public Long increment(Long coupondId) {

        String key = "coupon_" + coupondId + "_count";

        return redisTemplate
                .opsForValue()
                .increment(key);
    }

    public void delete(Long couponId) {

        String key = "coupon_" + couponId + "_count";
        redisTemplate.delete(key);
    }
}

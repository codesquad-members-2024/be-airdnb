package codesquad.team05.domain.coupon.kafka;

import codesquad.team05.domain.coupon.Coupon;
import codesquad.team05.domain.coupon.CouponRepository;
import codesquad.team05.domain.coupon.UserCoupon;
import codesquad.team05.domain.coupon.UserCouponRepository;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.web.coupon.dto.request.CouponMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponConsumer {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;


    @KafkaListener(topics = "coupon_create", groupId = "group1")
    public void listener(CouponMessage message) {

        User user = userRepository.findById(message.getUserId()).orElseThrow();
        Coupon coupon = couponRepository.findById(message.getCouponId()).orElseThrow();

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUser(user);
        userCoupon.setCoupon(coupon);

        userCouponRepository.save(userCoupon);
    }

}

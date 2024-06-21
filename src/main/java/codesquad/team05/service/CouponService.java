package codesquad.team05.service;

import codesquad.team05.domain.coupon.Coupon;
import codesquad.team05.domain.coupon.CouponCountRepository;
import codesquad.team05.domain.coupon.CouponRepository;
import codesquad.team05.domain.coupon.kafka.CouponProducer;
import codesquad.team05.web.coupon.dto.request.CouponMessage;
import codesquad.team05.web.coupon.dto.request.CouponSave;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {

    private final CouponCountRepository couponCountRepository;
    private final CouponRepository couponRepository;
    private final CouponProducer couponProducer;


    public void apply(Long userId, Long couponId) {
        long count = couponCountRepository.increment(couponId);
        //쿠폰의 개수
        Coupon coupon = couponRepository.findById(couponId).orElseThrow();

        if (count > coupon.getCountOfCoupon()) {
            return;
        }

        CouponMessage message = CouponMessage.toMessage(userId, couponId);

        couponProducer.create(message);
    }

    //테스트를 해도 실패할 수 있음

    public void create(CouponSave couponSave) {

        Coupon coupon = Coupon.toEntity(couponSave.getDiscountRate()
                , couponSave.getExpiredAt()
                , couponSave.getMinimumAmount()
                , couponSave.getMaximumDiscountAmount()
                , couponSave.getAccommodationType()
                , couponSave.getCountOfCoupon());

        couponRepository.save(coupon);
    }

}

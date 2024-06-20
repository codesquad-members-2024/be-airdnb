package com.airdnb.coupon;

import com.airdnb.coupon.dto.CouponDetail;
import com.airdnb.coupon.entity.Coupon;
import com.airdnb.coupon.entity.CouponStatus;
import com.airdnb.coupon.repository.AppliedMemberRepository;
import com.airdnb.coupon.repository.CouponCountRepository;
import com.airdnb.coupon.repository.CouponRepository;
import com.airdnb.global.exception.InvalidRequestException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CouponService {
    private static final int MAX_CREATE_COUNT = 100;
    private static final double DISCOUNT_RATE = 50;

    private final CouponRepository couponRepository;
    private final CouponCountRepository couponCountRepository;
    private final AppliedMemberRepository appliedMemberRepository;


    public Long createCoupon(String memberId) {
        Long added = appliedMemberRepository.add(memberId);
        if (added != 1) {
            throw new InvalidRequestException("이미 쿠폰이 발급된 사용자입니다.");
        }

        Long couponCount = couponCountRepository.increment();

        if (couponCount > MAX_CREATE_COUNT) {
            throw new InvalidRequestException("발급 가능한 쿠폰이 모두 소진되었습니다.");
        }

        Coupon coupon = new Coupon(DISCOUNT_RATE, CouponStatus.ACTIVE, memberId);
        couponRepository.save(coupon);
        return coupon.getId();
    }

    @Transactional(readOnly = true)
    public List<CouponDetail> queryCoupons(String memberId) {
        List<Coupon> coupons = couponRepository.findAllByMemberId(memberId);
        return coupons.stream()
                .map(CouponDetail::from)
                .toList();
    }
}

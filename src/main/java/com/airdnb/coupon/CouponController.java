package com.airdnb.coupon;

import com.airdnb.global.ApiResponse;
import com.airdnb.security.SecurityMemberProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {
    private final CouponService couponService;

    @PostMapping
    public ApiResponse createCoupon() {
        String currentMemberId = SecurityMemberProvider.getCurrentMemberId();
        Long couponId = couponService.createCoupon(currentMemberId);
        
        return ApiResponse.success(couponId);
    }
}

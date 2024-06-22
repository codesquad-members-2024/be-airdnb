package codesquad.team05.web.coupon.dto.request;

import lombok.Getter;

@Getter
public class CouponMessage {


    private Long userId;
    private Long couponId;


    public static CouponMessage toMessage(Long userId, Long couponId) {
        CouponMessage message = new CouponMessage();
        message.userId = userId;
        message.couponId = couponId;
        return message;

    }

}

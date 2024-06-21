package codesquad.team05.web.coupon.dto.request;

import codesquad.team05.domain.accomodation.AccommodationType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CouponSave {

    @NotNull
    private Integer discountRate;
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate expiredAt;
    @NotNull
    private AccommodationType accommodationType;
    @NotNull
    private Integer minimumAmount;
    @NotNull
    private Integer maximumDiscountAmount;
    @NotNull
    private Boolean canCombineWithOtherCoupons;
    @NotNull
    private Integer countOfCoupon;


}

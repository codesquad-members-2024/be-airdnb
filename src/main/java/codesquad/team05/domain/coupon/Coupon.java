package codesquad.team05.domain.coupon;

import codesquad.team05.domain.accomodation.AccommodationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer discountRate;

    @Column(nullable = false)
    private LocalDate registeredAt;

    @Column(nullable = false)
    private LocalDate expiredAt;

    private Integer minimumAmount;

    private Integer maximumDiscountAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccommodationType accommodationType;

    @Column(nullable = false)
    private Integer countOfCoupon;

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.PERSIST)
    List<UserCoupon> userCoupons = new ArrayList<>();

    public static Coupon toEntity(Integer discountRate
            , LocalDate expiredAt
            , Integer minimumAmount
            , Integer maximumDiscountAmount
            , AccommodationType accommodationType
            , Integer countOfCoupon) {

        Coupon coupon = new Coupon();
        coupon.discountRate = discountRate;
        coupon.registeredAt = LocalDate.now();
        coupon.expiredAt = expiredAt;
        coupon.minimumAmount = minimumAmount;
        coupon.maximumDiscountAmount = maximumDiscountAmount;
        coupon.accommodationType = accommodationType;
        coupon.countOfCoupon = countOfCoupon;

        return coupon;
    }
}

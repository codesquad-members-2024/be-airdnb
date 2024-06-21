package codesquad.team05.domain.coupon;

import codesquad.team05.domain.accomodation.AccommodationType;
import codesquad.team05.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    Coupon coupon;

    public void setUser(User user) {
        this.user = user;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public boolean isOwnedBy(Long userId) {
        return this.user.getId().equals(userId);
    }

    public boolean isAboveMinimumAmount(Double amount) {
        return this.coupon.getMinimumAmount() < amount;
    }

    public double calculateCouponAmount(double amount) {
        return this.coupon.getDiscountRate() * amount;
    }

    public int getMaximumDiscountAmount() {
        return coupon.getMaximumDiscountAmount();
    }

    public boolean validateAccommodationType(AccommodationType type) {
        return this.coupon.getAccommodationType().equals(type);
    }

    public boolean validateExpirationDate(LocalDate expirationDate) {
        return this.coupon.getExpiredAt().isBefore(expirationDate);
    }

}

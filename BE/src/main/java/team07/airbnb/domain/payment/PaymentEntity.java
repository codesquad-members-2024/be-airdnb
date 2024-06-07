package team07.airbnb.domain.payment;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.booking.dto.response.BookingInfo;
import team07.airbnb.domain.booking.entity.BookingEntity;

@Entity
@Table(name = "PAYMENT")
@AllArgsConstructor
@NoArgsConstructor
public class PaymentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Fee fee;
    private long totalPrice;
    private PaymentStatus status;


    public static PaymentEntity of(BookingInfo bookingInfo) {
        Fee fee = Fee.of(bookingInfo);
        long totalPrice = bookingInfo.getRoughTotalPrice() - bookingInfo.getDiscountPrice() + fee.getAccommodationFee() + fee.getServiceFee();
        return new PaymentEntity(
                null,
                fee,
                totalPrice,
                PaymentStatus.FENDING
        );
    }
}

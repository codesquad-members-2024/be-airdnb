package team07.airbnb.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.entity.embed.Fee;
import team07.airbnb.data.payment.PaymentStatus;
import team07.airbnb.data.booking.dto.PriceInfo;

@Entity
@Table(name = "PAYMENT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PaymentEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Fee fee;

    private int totalPrice;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;


    public static PaymentEntity of(PriceInfo priceInfo) {
        Fee fee = Fee.of(priceInfo.getServiceFee(), priceInfo.getAccommodationFee());
        int totalPrice = priceInfo.getRoughTotalPrice() - priceInfo.getDiscountPrice() + fee.getAccommodationFee() + fee.getServiceFee();
        return new PaymentEntity(
                null,
                fee,
                totalPrice,
                PaymentStatus.FENDING
        );
    }
}

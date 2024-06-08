package team07.airbnb.domain.payment;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.booking.entity.BookingEntity;

@Entity
@Table(name = "PAYMENT")
public class PaymentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
//    @OneToOne
//    private BookingEntity booking;
    @Embedded
    private Fee fee;
//    private int totalPrice = booking.getTotalPrice() + fee.getTotalFee();
    private PaymentStatus status;
}

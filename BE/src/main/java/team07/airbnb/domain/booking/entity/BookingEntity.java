package team07.airbnb.domain.booking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.discount.entity.DiscountPolicyEntity;
import team07.airbnb.domain.booking.property.BookingStatus;
import team07.airbnb.domain.payment.PaymentEntity;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.product.entity.ProductEntity;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Table(name = "BOOKING")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<ProductEntity> products;
    @ManyToOne
    private UserEntity booker;
    private Integer headCount;

    private LocalDate checkin;
    private LocalDate checkout;
    private BookingStatus status;

    @OneToOne
    private PaymentEntity payment;
}

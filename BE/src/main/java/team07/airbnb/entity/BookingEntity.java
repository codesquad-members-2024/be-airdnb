package team07.airbnb.entity;

import jakarta.persistence.CascadeType;
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
import lombok.Setter;
import team07.airbnb.domain.booking.property.BookingStatus;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    @ManyToOne
    private UserEntity booker;

    @ManyToOne
    private UserEntity host;

    private Integer headCount;

    private LocalDate checkin;

    private LocalDate checkout;

    @Setter
    private BookingStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    private PaymentEntity payment;

    @OneToOne(cascade = CascadeType.ALL)
    private ReviewEntity review;

    public BookingEntity addReview(ReviewEntity review) {
        this.review = review;
        return this;
    }
}

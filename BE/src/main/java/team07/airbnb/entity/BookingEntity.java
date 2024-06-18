package team07.airbnb.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import team07.airbnb.data.booking.enums.BookingStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "BOOKING")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class BookingEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "booker_id")
    private UserEntity booker;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private UserEntity host;

    private Integer headCount;

    private LocalDate checkin;

    private LocalDate checkout;

    @Enumerated(EnumType.STRING)
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

    public void confirmBooking() {
        this.status = BookingStatus.CONFIRM;
    }

    public void completeBooking() {
        this.status = BookingStatus.COMPLETE;
    }

    public void cancelBooking() {
        this.status = BookingStatus.CANCEL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BookingEntity) {
            return this.id.equals(((BookingEntity) obj).id);
        }
        return false;
    }
}

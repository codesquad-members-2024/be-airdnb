package team07.airbnb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.data.product.ProductStatus;
import team07.airbnb.exception.bad_request.IllegalProductStateException;

import java.time.LocalDate;

@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class ProductEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private AccommodationEntity accommodation;

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private BookingEntity booking;

    private LocalDate date;

    private Integer price;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    public static ProductEntity ofOpen(AccommodationEntity accommodation, LocalDate date, int price) {
        return ProductEntity.builder()
                .accommodation(accommodation)
                .date(date)
                .price(price)
                .status(ProductStatus.OPEN)
                .build();
    }

    public ProductEntity book(BookingEntity booking){
        if(!canBook())
            throw new IllegalProductStateException("[ %d번 상품 예약 실패]예약 불가 상태 상품입니다.".formatted(this.id), "Booking is null = {%b} & Status = {%s}".formatted(this.booking == null, this.status.toString()));

        this.booking = booking;
        this.status = ProductStatus.BOOKED;

        return this;
    }

    public ProductEntity open(BookingEntity booking) {
        if (!canOpen())
            throw new IllegalProductStateException("[ %d번 상품 오픈 실패]오픈 불가 상태 상품입니다.".formatted(this.id), "Booking is null = {%b} & Status = {%s}".formatted(this.booking == null, this.status.toString()));

        this.booking = null;
        this.status = ProductStatus.OPEN;

        return this;
    }

    private boolean canOpen() {
        return this.booking != null && this.status == ProductStatus.BOOKED;
    }

    private boolean canBook() {
        return this.booking == null && this.status == ProductStatus.OPEN;
    }
}

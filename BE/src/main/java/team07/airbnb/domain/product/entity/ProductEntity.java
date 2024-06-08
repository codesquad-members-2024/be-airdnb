package team07.airbnb.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.domain.booking.entity.BookingEntity;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOMMODATION_PRODUCT")
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private AccommodationEntity accommodation;

    @ManyToOne
    private BookingEntity booking;

    private LocalDate date;

    private int price;

    private ProductStatus status;

    public static ProductEntity ofOpen(AccommodationEntity accommodation, LocalDate date, int price){
        return ProductEntity.builder()
                .accommodation(accommodation)
                .date(date)
                .price(price)
                .status(ProductStatus.OPEN)
                .build();
    }
}

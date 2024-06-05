package team07.airbnb.domain.product.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.accommodation.entity.AccomodationEntity;
import team07.airbnb.domain.booking.DiscountPolicy;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "ACCOMODATION_PRODUCT")
public class ProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private AccomodationEntity accomodation;
    private LocalDate date;
    private int price;
    private ProductStatus status;
}

package team07.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.data.accommodation.enums.AccommodationType;
import team07.airbnb.data.product.ProductStatus;
import team07.airbnb.entity.embed.AccommodationLocation;
import team07.airbnb.entity.embed.RoomInformation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACCOMMODATION")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class AccommodationEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "host_id")
    private UserEntity host;

    private AccommodationType type;

    @Embedded
    private RoomInformation roomInformation;

    @Embedded
    private AccommodationLocation address;

    // Description
    private String name;

    private String description;

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pictures> pictures = new ArrayList<>();

    private int basePricePerDay;

    @JsonIgnore
    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductEntity> products = new ArrayList<>();

    @Transient
    public List<ReviewEntity> reviews() {
        return products.stream()
                .map(ProductEntity::getBooking)
                .map(BookingEntity::getReview)
                .toList();
    }

    public List<ProductEntity> getOpenProducts() {
        return this.getProducts().stream()
                .filter(product -> product.getStatus().equals(ProductStatus.OPEN))
                .toList();
    }

    public double rating() {
        return reviews().stream().mapToDouble(ReviewEntity::getRating).average().orElseGet(() -> 0.0);
    }

    public void addPicture(String url) {
        pictures.add(new Pictures(this, url));
    }

    public void addProduct(LocalDate date, int price) {
        products.add(ProductEntity.ofOpen(this, date, price));
    }

    public void addProduct(LocalDate date) {
        products.add(ProductEntity.ofOpen(this, date, basePricePerDay));
    }
}

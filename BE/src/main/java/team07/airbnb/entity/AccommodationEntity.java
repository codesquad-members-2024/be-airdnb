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
import java.util.Objects;

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

    public List<ReviewEntity> reviews() {
        return products.stream()
                .map(ProductEntity::getBooking)
                .filter(Objects::nonNull)
                .map(BookingEntity::getReview)
                .filter(Objects::nonNull)
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

    public void updateRoomInfo(RoomInformation roomInformation){
        this.roomInformation = roomInformation;
    }

    public void updateDescription(String name , String description){
        this.name = name;
        this.description = description;
    }

    public void updateBaseInfo(AccommodationType type, AccommodationLocation address, Integer basePricePerDay){
        this.type = type;
        this.address = address;
        this.basePricePerDay = basePricePerDay;
    }

    public void updatePictures(List<Pictures> pictures){
        this.pictures.forEach(Pictures::detach);
        this.pictures = pictures;
    }
}

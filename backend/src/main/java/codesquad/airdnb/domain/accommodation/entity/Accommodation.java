package codesquad.airdnb.domain.accommodation.entity;

import codesquad.airdnb.domain.accommodation.entity.embedded.FloorPlan;
import codesquad.airdnb.domain.accommodation.entity.embedded.Location;
import codesquad.airdnb.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ACCOMMODATION")
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "HOST_ID")
    private Member host;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PLACE_CATEGORY")
    private String placeCategory;

    @Column(name = "BASE_PRICE_PER_NIGHT")
    private Long basePricePerNight;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CHECK_IN_TIME")
    private Time checkInTime;

    @Column(name = "CHECK_OUT_TIME")
    private Time checkOutTime;

    @Embedded
    @Builder.Default
    private FloorPlan floorPlan = new FloorPlan();

    @Embedded
    @Builder.Default
    private Location location = new Location();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AccoImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AccoAmen> amenities = new ArrayList<>();

    public void addAmenities(List<AccoAmen> amenities) {
        this.amenities = amenities;
    }

    public void addImages(List<AccoImage> images) {
        this.images = images;
    }

    public List<String> getImageUrls() {
        return images.stream()
                .map(AccoImage::getUrl)
                .toList();
    }

    public List<String> getAmenityNames() {
        return amenities.stream()
                .map(AccoAmen::getAmenity)
                .map(Amenity::getName)
                .toList();
    }
}



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
    private Long id;

    @ManyToOne
    @JoinColumn(name = "HOST_ID")
    private Member host;

    private String title;

    private String placeCategory;

    private Long price;

    private String description;

    private Time checkInTime;

    private Time checkOutTime;

    @Embedded
    private FloorPlan floorPlan = new FloorPlan();

    @Embedded
    private Location location = new Location();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccoImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "accommodation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AccoAmen> amenities = new ArrayList<>();

    public void addAmenities(List<AccoAmen> amenities) {
        this.amenities = amenities;
    }

    public void addImages(List<AccoImage> images) {
        this.images = images;
    }
}



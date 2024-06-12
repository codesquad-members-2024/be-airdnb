package team03.airdnb.accommodationAmenity;

import jakarta.persistence.*;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.amenity.Amenity;
import team03.airdnb.common.BaseEntity;

@Entity
@Table(name = "accommodation_amenity")
@Getter
public class AccommodationAmenity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "amenity_id")
    private Amenity amenity;
}

package team03.airdnb.AccommodationAmenity;

import jakarta.persistence.*;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.amenity.Amenity;

@Entity
@Table(name = "ACCOMMODATIONS_AMENITY")
@Getter
public class AccommodationAmenity {

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

package team10.airdnb.accommodation_amenity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.amenity.entity.Amenity;

@Entity
@Table(name = "accommodation_amenity")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccommodationAmenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @ManyToOne
    @JoinColumn(name = "amenity_id", nullable = false)
    private Amenity amenity;

}

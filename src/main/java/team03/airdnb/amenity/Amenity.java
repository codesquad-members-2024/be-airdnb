package team03.airdnb.amenity;

import jakarta.persistence.*;
import lombok.Getter;
import team03.airdnb.accommodationAmenity.AccommodationAmenity;
import team03.airdnb.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Amenity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "amenity")
    private List<AccommodationAmenity> accommodationAmenities = new ArrayList<>();

}

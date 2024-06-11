package team10.airdnb.amenity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToMany;
import org.springframework.data.annotation.Id;
import team10.airdnb.accommodation.entity.Accommodation;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "amenities")
    private Set<Accommodation> accommodations = new HashSet<>();
}


package team07.airbnb.domain.accommodation.property;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class AccommodationAmenity {
    @Id
    private String name;
}

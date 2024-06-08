package team07.airbnb.domain.accommodation.property;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Embeddable
@NoArgsConstructor
@Getter
public class AccommodationLocation {
    private String address;
    private Integer zipCode;
    private Point point;
}

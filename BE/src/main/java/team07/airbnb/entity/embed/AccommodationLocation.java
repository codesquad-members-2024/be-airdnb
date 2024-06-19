package team07.airbnb.entity.embed;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.locationtech.jts.geom.Point;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class AccommodationLocation {
    private String address;
    private Integer zipCode;
    private Point point;
}

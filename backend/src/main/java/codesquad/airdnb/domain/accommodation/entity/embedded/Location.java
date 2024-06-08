package codesquad.airdnb.domain.accommodation.entity.embedded;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Embeddable
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Location {

    private String country;

    private String province;

    private String city;

    private String district;

    private String streetAddress;

    private String streetAddressDetail;

    private String postalCode;

    private Point coordinate;
}

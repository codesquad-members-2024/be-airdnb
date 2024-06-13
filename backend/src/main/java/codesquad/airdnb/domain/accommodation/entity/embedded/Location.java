package codesquad.airdnb.domain.accommodation.entity.embedded;

import jakarta.persistence.Column;
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

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "STREET_ADDRESS")
    private String streetAddress;

    @Column(name = "STREET_ADDRESS_DETAIL")
    private String streetAddressDetail;

    @Column(name = "POSTAL_CODE")
    private String postalCode;

    @Column(name = "COORDINATE")
    private Point coordinate;
}

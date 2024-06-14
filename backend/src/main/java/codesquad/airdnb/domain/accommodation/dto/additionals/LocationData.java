package codesquad.airdnb.domain.accommodation.dto.additionals;

import codesquad.airdnb.domain.accommodation.entity.embedded.Location;
import jakarta.validation.constraints.*;
import lombok.Builder;
import org.locationtech.jts.geom.Point;

@Builder
public record LocationData (

    @NotBlank
    @Size(min = 2, max = 2)
    String country,

    @NotBlank
    String province,

    String city,

    @NotBlank
    String district,

    @NotBlank
    String streetAddress,

    String streetAddressDetail,

    @Size(min = 1, max = 100)
    String postalCode,

    Point point
) {

    public Location toEmbedded() {
        return Location.builder()
                .country(country)
                .province(province)
                .city(city)
                .district(district)
                .streetAddress(streetAddress)
                .streetAddressDetail(streetAddressDetail)
                .postalCode(postalCode)
                .coordinate(point)
                .build();
    }

    public static LocationData of(Location location) {
        return LocationData.builder()
                .country(location.getCountry())
                .province(location.getProvince())
                .city(location.getCity())
                .district(location.getDistrict())
                .streetAddress(location.getStreetAddress())
                .streetAddressDetail(location.getStreetAddressDetail())
                .postalCode(location.getPostalCode())
                .point(location.getCoordinate())
                .build();
    }
}

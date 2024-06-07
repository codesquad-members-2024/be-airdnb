package codesquad.airdnb.domain.accommodation.dto.additionals;

import codesquad.airdnb.domain.accommodation.entity.embedded.Location;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

import static codesquad.airdnb.env.Constants.SRID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationData {

    @NotBlank
    @Size(min = 2, max = 2)
    private String country;

    @NotBlank
    private String province;

    private String city;

    @NotBlank
    private String district;

    @NotBlank
    private String streetAddress;

    private String streetAddressDetail;

    private String postalCode;

    @NotNull
    @Min(value = -90)
    @Max(value = 90)
    private Double coordinateX;

    @NotNull
    @Min(value = -180)
    @Max(value = 180)
    private Double coordinateY;

    private Point createPoint(Double coordinateX, Double coordinateY) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(coordinateX, coordinateY));
        point.setSRID(SRID);
        return point;
    }

    public Location toEmbedded() {
        return Location.builder()
                .country(country)
                .province(province)
                .city(city)
                .district(district)
                .streetAddress(streetAddress)
                .streetAddressDetail(streetAddressDetail)
                .postalCode(postalCode)
                .coordinate(createPoint(coordinateX, coordinateY))
                .build();
    }

    public static LocationData toResponseEmbedded(Location location) {
        return LocationData.builder()
                .country(location.getCountry())
                .province(location.getProvince())
                .city(location.getCity())
                .district(location.getDistrict())
                .streetAddress(location.getStreetAddress())
                .streetAddressDetail(location.getStreetAddressDetail())
                .postalCode(location.getPostalCode())
                .coordinateX(location.getCoordinate().getX())
                .coordinateY(location.getCoordinate().getY())
                .build();
    }
}

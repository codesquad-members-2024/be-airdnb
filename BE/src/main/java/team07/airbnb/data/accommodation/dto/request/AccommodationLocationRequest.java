package team07.airbnb.data.accommodation.dto.request;

import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Range;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import team07.airbnb.entity.embed.AccommodationLocation;

public record AccommodationLocationRequest(
        @NotNull
        String address,

        @NotNull
        @Range(min = 10000, max = 99999)
        Integer zipCode,

        @NotNull
        @Range(min = -180, max = 180)
        Double longitude,

        @NotNull
        @Range(min = -90, max = 90)
        Double latitude
) {

    public AccommodationLocation toLocation() {
        return new AccommodationLocation(
                this.address,
                this.zipCode,
                getPoint()
        );
    }

    public Point getPoint() {
        Point pointType = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
        pointType.setSRID(4326);

        return pointType;
    }
}

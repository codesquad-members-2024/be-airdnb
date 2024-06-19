package team07.airbnb.data.accommodation.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import team07.airbnb.entity.embed.AccommodationLocation;

public record AccommodationLocationRequest(
        @NotNull
        String address,

        @NotNull
        @Min(10000) @Max(99999)
        Integer zipCode,

        @NotNull
        @Min(-180) @Max(180)
        Double longitude,

        @NotNull
        @Min(-90) @Max(90)
        Double latitude
) {

    public AccommodationLocation toLocation() {
        return new AccommodationLocation(
                this.address,
                this.zipCode,
                point()
        );
    }

    public Point point() {
        Point pointType = new GeometryFactory().createPoint(new Coordinate(longitude, latitude));
        pointType.setSRID(4326);

        return pointType;
    }
}

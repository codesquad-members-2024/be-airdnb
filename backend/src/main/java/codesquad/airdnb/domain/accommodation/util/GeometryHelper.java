package codesquad.airdnb.domain.accommodation.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import static codesquad.airdnb.global.env.Constants.SRID;

@Component
public class GeometryHelper {

    private final GeometryFactory geometryFactory = new GeometryFactory();

    public Point createPoint(Double longitude, Double latitude) {
        Point point = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        point.setSRID(SRID);
        return point;
    }
}
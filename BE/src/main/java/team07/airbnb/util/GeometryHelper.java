package team07.airbnb.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

@Component
public class GeometryHelper {
    private final GeometryFactory geometryFactory = new GeometryFactory();

    public Point getPoint(double longitude, double latitude) {
        Point center = geometryFactory.createPoint(new Coordinate(longitude, latitude));
        center.setSRID(4326);

        return center;
    }
}

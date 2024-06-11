package team07.airbnb.config.jackson.deserializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import team07.airbnb.common.util.GeometryHelper;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomPointDeserializer extends JsonDeserializer<Point> {
    private final GeometryHelper geometryHelper;

    @Override
    public Point deserialize(JsonParser p, DeserializationContext context) throws IOException {
        p.nextToken(); // JsonToken.START_ARRAY
        double lng = p.getDoubleValue();
        p.nextToken(); // JsonToken.VALUE_NUMBER_FLOAT
        double lat = p.getDoubleValue();
        p.nextToken(); // JsonToken.VALUE_NUMBER_FLOAT

        return geometryHelper.getPoint(lng, lat);
    }
}


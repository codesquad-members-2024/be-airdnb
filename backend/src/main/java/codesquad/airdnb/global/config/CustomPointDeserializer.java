package codesquad.airdnb.global.config;

import codesquad.airdnb.domain.accommodation.util.GeometryHelper;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;

import java.io.IOException;

@RequiredArgsConstructor
public class CustomPointDeserializer extends JsonDeserializer<Point> {

    private final GeometryHelper geometryHelper;

    @Override
    public Point deserialize(JsonParser p, DeserializationContext context) throws IOException {
        JsonNode node = p.getCodec().readTree(p);
        double longitude = node.get("longitude").asDouble();
        double latitude = node.get("latitude").asDouble();
        return geometryHelper.createPoint(longitude, latitude);
    }
}

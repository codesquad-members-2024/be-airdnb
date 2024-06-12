package codesquad.airdnb.global.config;

import codesquad.airdnb.domain.accommodation.util.GeometryHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.locationtech.jts.geom.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean("customObjectMapper")
    public ObjectMapper objectMapper(GeometryHelper geometryHelper) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();

        module.addSerializer(Point.class, new CustomPointSerializer());
        module.addDeserializer(Point.class, new CustomPointDeserializer(geometryHelper));

        mapper.registerModule(module);

        return mapper;
    }
}

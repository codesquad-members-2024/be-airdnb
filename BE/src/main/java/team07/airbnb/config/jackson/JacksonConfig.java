package team07.airbnb.config.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.locationtech.jts.geom.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team07.airbnb.config.jackson.CustomLocalDateDeserializer;
import team07.airbnb.config.jackson.CustomLocalDateTimeSerializer;
import team07.airbnb.config.jackson.CustomPointSerializer;
import team07.airbnb.util.GeometryHelper;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper(GeometryHelper geometryHelper) {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        module.addSerializer(Point.class, new CustomPointSerializer());
        module.addDeserializer(LocalDate.class, new CustomLocalDateDeserializer());
        module.addDeserializer(Point.class, new CustomPointDeserializer(geometryHelper));
        mapper.registerModule(module);

        return mapper;
    }
}


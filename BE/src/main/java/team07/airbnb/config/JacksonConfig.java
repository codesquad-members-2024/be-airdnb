package team07.airbnb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.locationtech.jts.geom.Point;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team07.airbnb.config.jackson.CustomLocalDateTimeSerializer;
import team07.airbnb.config.jackson.CustomPointSerializer;

import java.time.LocalDateTime;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDateTime.class, new CustomLocalDateTimeSerializer());
        module.addSerializer(Point.class, new CustomPointSerializer());
        mapper.registerModule(module);

        return mapper;
    }
}


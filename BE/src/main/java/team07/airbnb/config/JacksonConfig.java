package team07.airbnb.config;

import com.bedatadriven.jackson.datatype.jts.JtsModule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        // Register JTS module for Point
        mapper.registerModule(new JtsModule());

        // Register Java Time module for LocalDateTime
        mapper.registerModule(new JavaTimeModule());

        return mapper;
    }
}


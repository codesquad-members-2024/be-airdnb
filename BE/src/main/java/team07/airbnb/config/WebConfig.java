package team07.airbnb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.ForwardedHeaderFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import team07.airbnb.common.auth.jwt.TokenArgumentResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TokenArgumentResolver tokenArgumentResolver;

    public WebConfig(TokenArgumentResolver tokenArgumentResolver) {
        this.tokenArgumentResolver = tokenArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(tokenArgumentResolver);
    }

    @Bean
    ForwardedHeaderFilter forwardedHeaderFilter() {
        return new ForwardedHeaderFilter();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://squadbnb.site")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}


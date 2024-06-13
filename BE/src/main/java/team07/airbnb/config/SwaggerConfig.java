package team07.airbnb.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server server = new Server();
        server.setUrl("https://squadbnb/api");

        return new OpenAPI()
                .servers(List.of(server))
                .info(new Info()
                        .title("Squadbnb API")
                        .version("1.0")
                        .description("스쿼드비엔비 API 문서입니다"))
                        .tags(Arrays.asList(
                                new Tag().name("User"),
                                new Tag().name("Host"),
                                new Tag().name("숙소"),
                                new Tag().name("상품"),
                                new Tag().name("예약"),
                                new Tag().name("리뷰")
                        ));
    }
}


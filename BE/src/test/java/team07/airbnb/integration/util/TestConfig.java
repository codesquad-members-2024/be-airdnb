package team07.airbnb.integration.util;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @Value("${jwt.host}")
    private String HOST_TOKEN;

    @Value("${jwt.guest}")
    private String GUEST_TOKEN;

    @Bean(name = "hostRequest")
    public Request requestInstanceOfHost() {
        Request request = new Request();
        request.setJwtToken(HOST_TOKEN);

        return request;
    }

    @Bean(name = "guestRequest")
    public Request requestInstanceOfGuest() {
        Request request = new Request();
        request.setJwtToken(GUEST_TOKEN);

        return request;
    }

    @Bean(name = "monkey")
    public FixtureMonkey fixtureMonkey(){
        return FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }
}


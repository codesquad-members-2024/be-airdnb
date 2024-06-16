package team07.airbnb.integration.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import team07.airbnb.data.user.enums.Role;

@Configuration
public class TestConfig {

    @Autowired
    private AuthHelper authHelper;

    @Bean(name = "hostRequest")
    public Request requestInstanceOfHost() throws JsonProcessingException {
        Request request = new Request();
        request.setJwtToken(authHelper.login(Role.HOST));

        return request;
    }

    @Bean(name = "guestRequest")
    public Request requestInstanceOfGuest() throws JsonProcessingException {
        Request request = new Request();
        request.setJwtToken(authHelper.login(Role.USER));

        return request;
    }

    @Bean(name = "otherRequest")
    public Request requestInstanceOfOther() throws JsonProcessingException {
        Request request = new Request();
        request.setJwtToken(authHelper.login(Role.USER));

        return request;
    }

    @Bean(name = "monkey")
    public FixtureMonkey fixtureMonkey() {
        return FixtureMonkey.builder()
                .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
                .plugin(new JakartaValidationPlugin())
                .build();
    }
}


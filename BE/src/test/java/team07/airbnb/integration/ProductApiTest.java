package team07.airbnb.integration;

import com.navercorp.fixturemonkey.FixtureMonkey;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Profile;
import team07.airbnb.integration.util.DataBaseHelper;
import team07.airbnb.integration.util.Request;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public class ProductApiTest {

    @Autowired
    Request hostRequest;
    @Autowired
    Request userRequest;
    @Autowired
    FixtureMonkey monkey;
    @Autowired
    DataBaseHelper dataBaseHelper;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @AfterEach
    void tearDown() {
        dataBaseHelper.clear();
    }

    @DisplayName("")
    @Test
    void test() {
        // given

        // when

        //then
    }
}

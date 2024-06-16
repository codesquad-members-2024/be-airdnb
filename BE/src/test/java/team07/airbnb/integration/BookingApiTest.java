package team07.airbnb.integration;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import team07.airbnb.integration.util.Request;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingApiTest {

    @Autowired
    Request request;
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp()
    {
        RestAssured.port = port;
    }

    @DisplayName("")
    @Test
    void test() {
        // given
        ExtractableResponse<Response> response = request.get("favorite/my");
        // when

        //then
        System.out.println(response.body());
    }
}

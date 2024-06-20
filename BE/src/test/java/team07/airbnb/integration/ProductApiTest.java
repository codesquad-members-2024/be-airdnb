package team07.airbnb.integration;

import com.navercorp.fixturemonkey.FixtureMonkey;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
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

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

    @DisplayName("필터 적용 테스트")
    @Test
    void filter() {
        // given
        List<String> requests = List.of(
                "/products/available?minPrice=10000&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?minPrice=5000&maxPrice=20000&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?checkInDate=2024-07-01&checkOutDate=2024-07-10&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?headCount=4&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?minPrice=10000&maxPrice=25000&checkInDate=2024-08-01&checkOutDate=2024-08-15&headCount=2&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?minPrice=15000&maxPrice=30000&headCount=3&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?checkInDate=2024-06-20&checkOutDate=2024-06-25&headCount=5&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?minPrice=5000&maxPrice=20000&checkInDate=2024-09-01&checkOutDate=2024-09-10&headCount=2&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?minPrice=8000&checkInDate=2024-07-15&checkOutDate=2024-07-20&latitude=37.7749&longitude=-122.4194&distance=10.0",
                "/products/available?minPrice=12000&headCount=3&latitude=37.7749&longitude=-122.4194&distance=10.0"
        );

        // when
        //then
        for(String request : requests) {
            ExtractableResponse<Response> response = userRequest.get(request);
            assertThat(response.statusCode()).isEqualTo(200);
        }
    }
}

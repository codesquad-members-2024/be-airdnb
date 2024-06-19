package team07.airbnb.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import team07.airbnb.data.accommodation.dto.request.AccommodationCreateRequest;
import team07.airbnb.data.accommodation.dto.response.AccommodationListResponse;
import team07.airbnb.integration.util.DataBaseHelper;
import team07.airbnb.integration.util.Request;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Profile("test")
public class AccommodationApiTest {

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

    @DisplayName("유저는 숙소를 등록할 수 있다.")
    @Test
    void hostAccommodation() throws JsonProcessingException {
        // given
        AccommodationCreateRequest acc = monkey.giveMeOne(AccommodationCreateRequest.class);

        // when
        AccommodationListResponse created = hostRequest.post(acc, "/accommodation",
                AccommodationListResponse.class);

        //then
        assertThat(created.name()).isEqualTo(acc.name());
    }

    @DisplayName("호스트는 본인이 등록한 속소를 조회할 수 있다.")
    @Test
    void test() throws JsonProcessingException {
        // given
        AccommodationCreateRequest acc = monkey.giveMeOne(AccommodationCreateRequest.class);
        AccommodationListResponse created = hostRequest.post(acc, "/accommodation"
                , AccommodationListResponse.class);
        
        // when
       ExtractableResponse<Response> response = hostRequest.get("/accommodation/my");

        //then
        assertThat(response.statusCode()).isEqualTo(200);
//        assertThat(response.jsonPath().getString("name")).isEqualTo(acc.name());
    }
}

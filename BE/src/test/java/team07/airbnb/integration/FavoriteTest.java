package team07.airbnb.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.context.ActiveProfiles;
import team07.airbnb.data.accommodation.dto.request.AccommodationCreateRequest;
import team07.airbnb.data.accommodation.dto.response.AccommodationListResponse;
import team07.airbnb.data.product.dto.request.ProductCreateRequest;
import team07.airbnb.data.user.dto.response.FavoritesResponse;
import team07.airbnb.integration.util.DataBaseHelper;
import team07.airbnb.integration.util.Request;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FavoriteTest {

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

    @DisplayName("유저는 상품을 위시리스트에 추가하고 조회할 수 있다.")
    @Test
    void addFavorite() throws JsonProcessingException {
        // given
        AccommodationCreateRequest acc = monkey.giveMeOne(AccommodationCreateRequest.class);
        AccommodationListResponse created = hostRequest.post(acc, "/accommodation", AccommodationListResponse.class);

        ProductCreateRequest pro = new ProductCreateRequest(created.id() , LocalDate.now(), 1000);
        hostRequest.post(pro, "/products");
        userRequest.post(null, "/favorite/" + 1);

        // when
        FavoritesResponse response = userRequest.get("favorite/my", FavoritesResponse.class);

        //then
        assertThat(response.available()).hasSize(1);
        assertThat(response.available().get(0).accommodation().id()).isEqualTo(created.id());
    }

    @DisplayName("유저는 상품을 위시리스트에서 삭제할 수 있다.")
    @Test
    void removeFavorite() throws JsonProcessingException {
        // given
        AccommodationCreateRequest acc = monkey.giveMeOne(AccommodationCreateRequest.class);
        AccommodationListResponse created = hostRequest.post(acc, "/accommodation", AccommodationListResponse.class);

        ProductCreateRequest pro = new ProductCreateRequest(created.id() , LocalDate.now(), 1000);
        hostRequest.post(pro, "/products");
        userRequest.post(null, "/favorite/" + 1);


        // when
        userRequest.delete( "/favorite/" + 1);
        FavoritesResponse response = userRequest.get("favorite/my", FavoritesResponse.class);

        //then
        assertThat(response.available()).hasSize(0);
    }
}

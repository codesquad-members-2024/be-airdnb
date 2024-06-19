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
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.booking.dto.response.BookingCreateResponse;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.ProductEntity;
import team07.airbnb.integration.util.AuthHelper;
import team07.airbnb.integration.util.DataBaseHelper;
import team07.airbnb.integration.util.Request;
import team07.airbnb.integration.util.TestConfig;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static team07.airbnb.data.user.enums.Role.HOST;
import static team07.airbnb.data.user.enums.Role.USER;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestConfig.class)
class BookingApiTest {

    //utils
    @Autowired
    Request hostRequest;

    @Autowired
    Request userRequest;

    @Autowired
    Request otherRequest;

    @Autowired
    DataBaseHelper dataBaseHelper;

    @Autowired
    AuthHelper auth;

    @Autowired
    FixtureMonkey monkey;

    //settings
    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        dataBaseHelper.clear();

        createDummyAccommodation();

        RestAssured.port = port;
    }

    @Test
    @DisplayName("존재하는 숙소에 상품, 날짜, 인원수가 정상적으로 설정되었으면 예약이 만들어져야함")
    void bookingCreateTest() throws JsonProcessingException {
        //given
        BookingRequest dummyRequest = monkey.giveMeBuilder(BookingRequest.class)
                .set("accommodationId", 1L)
                .set("checkIn", LocalDate.now())
                .set("checkOut", LocalDate.now().plusDays(5L))
                .set("headCount", 1)
                .sample();


        //when
        BookingCreateResponse response = userRequest.post(dummyRequest, "/booking", BookingCreateResponse.class);

        //then
        assertThat(response.checkIn()).isEqualTo(LocalDate.now());
        assertThat(response.checkOut()).isEqualTo(LocalDate.now().plusDays(5L));
        assertThat(response.headCount()).isEqualTo(1);
        assertThat(response.bookingId()).isEqualTo(1L);

    }

    @Test
    void getBookingPriceTest() throws JsonProcessingException {
        //given
        BookingRequest dummyRequest = monkey.giveMeBuilder(BookingRequest.class)
                .set("accommodationId", 1L)
                .set("checkIn", LocalDate.now())
                .set("checkOut", LocalDate.now().plusDays(5L))
                .set("headCount", 1)
                .sample();

        //when
        PriceInfo response = otherRequest.get(dummyRequest, "/booking", PriceInfo.class);

        //then
        assertThat(response.getRoughTotalPrice()).isEqualTo(60000);
        assertThat(response.getDiscountPrice()).isEqualTo((int) (60000 * 0.04));
    }

    void createDummyAccommodation() {
        List<Object> dummies = new ArrayList<>();
        AccommodationEntity dummyAC = monkey
                                        .giveMeBuilder(AccommodationEntity.class)
                                        .set("id", null)
                                        .sample();
        dummies.add(dummyAC);
        for (long i = 0; i < 10; i++) {
            dummies.add(ProductEntity.ofOpen(dummyAC, LocalDate.now().plusDays(i), 10000));
        }
        dataBaseHelper.put(dummies);
    }

    @AfterEach
    void shutDown() {
        System.out.println("shutdown");
        dataBaseHelper.clear();
    }
}

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
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.common.util.GeometryHelper;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.booking.dto.response.BookingCreateResponse;
import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.ProductEntity;
import team07.airbnb.entity.embed.AccommodationLocation;
import team07.airbnb.integration.util.AuthHelper;
import team07.airbnb.integration.util.DataBaseHelper;
import team07.airbnb.integration.util.Request;
import team07.airbnb.integration.util.TestConfig;

import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.as;
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
    @Autowired
    GeometryHelper geometryHelper;

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
    @DisplayName("숙소 아이디, 체크인, 체크아웃, 인원수를 주면 가격이 응답되어야된다.")
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

    @Test
    void confirmBooking() throws JsonProcessingException {
        //given
        bookingCreateTest();

        //when
        Long response = hostRequest.post("/booking/1", Long.class);

        //then
        assertThat(response).isEqualTo(1L);

        BookingEntity booking = dataBaseHelper.find(1L, BookingEntity.class);
        assertThat(booking.getStatus()).isEqualTo(BookingStatus.CONFIRM);
    }

    void createDummyAccommodation() {
        List<Object> dummies = new ArrayList<>();
        AccommodationLocation accommodationLocation = monkey.giveMeOne(AccommodationLocation.class);
        AccommodationEntity dummyAC = monkey
                                        .giveMeBuilder(AccommodationEntity.class)
                                        .set("id", null)
                                        .set("address",
                                                new AccommodationLocation(null, null,
                                                        geometryHelper.getPoint(1,1)))
                                        .sample();
        System.out.println(dummyAC.getAddress());
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

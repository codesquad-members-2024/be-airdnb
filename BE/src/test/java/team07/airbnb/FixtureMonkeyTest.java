package team07.airbnb;

import com.navercorp.fixturemonkey.FixtureMonkey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team07.airbnb.data.accommodation.dto.request.AccommodationCreateRequest;
import team07.airbnb.data.accommodation.dto.request.AccommodationDescriptionRequest;
import team07.airbnb.data.accommodation.dto.request.BaseAccommodationInfoRequest;
import team07.airbnb.data.booking.dto.request.BookingPaymentsRequest;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.product.dto.request.ProductCreateRequest;

@SpringBootTest
public class FixtureMonkeyTest {

    @Autowired
    FixtureMonkey monkey;

    @DisplayName("")
    @Test
    void test() {
        // given
        System.out.println(monkey.giveMeOne(AccommodationCreateRequest.class));
        System.out.println(monkey.giveMeOne(AccommodationDescriptionRequest.class));
        System.out.println(monkey.giveMeOne(BaseAccommodationInfoRequest.class));
        System.out.println(monkey.giveMeOne(BookingRequest.class));
        System.out.println(monkey.giveMeOne(BookingPaymentsRequest.class));
        System.out.println(monkey.giveMeOne(ProductCreateRequest.class));
        // when

        //then
    }

}

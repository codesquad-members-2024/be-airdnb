package team07.airbnb;

import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team07.airbnb.data.accommodation.dto.request.AccommodationCreateRequest;
import team07.airbnb.data.accommodation.dto.request.AccommodationDescriptionRequest;
import team07.airbnb.data.accommodation.dto.request.BaseAccommodationInfoRequest;
import team07.airbnb.data.booking.dto.request.BookingPaymentsRequest;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.product.dto.request.ProductCreateRequest;

public class FixtureMonkeyTest {

    private final FixtureMonkey sut = FixtureMonkey.builder()
            .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
            .plugin(new JakartaValidationPlugin())
            .build();

    @DisplayName("")
    @Test
    void test() {
        // given
        System.out.println(sut.giveMeOne(AccommodationCreateRequest.class));
        System.out.println(sut.giveMeOne(AccommodationDescriptionRequest.class));
        System.out.println(sut.giveMeOne(BaseAccommodationInfoRequest.class));
        System.out.println(sut.giveMeOne(BookingRequest.class));
        System.out.println(sut.giveMeOne(BookingPaymentsRequest.class));
        System.out.println(sut.giveMeOne(ProductCreateRequest.class));
        // when

        //then
    }

}

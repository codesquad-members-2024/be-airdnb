package team07.airbnb.domain.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.booking.dto.request.BookingRequestInfo;
import team07.airbnb.domain.booking.dto.response.BookingInfo;
import team07.airbnb.domain.booking.exception.InvalidDateException;
import team07.airbnb.domain.booking.price_policy.discount.beans.DiscountPolicy;
import team07.airbnb.domain.booking.price_policy.fee.AccommodationFee;
import team07.airbnb.domain.booking.price_policy.fee.ServiceFee;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DiscountPolicy discountPolicy;
    private final ServiceFee serviceFee;
    private final AccommodationFee accommodationFee;

    public long getRoughTotalPrice(long avgPrice, LocalDate checkIn, LocalDate checkOut) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days <= 0) throw new InvalidDateException();

        return avgPrice * days;
    }

    public long getDiscountPrice(long roughTotalPrice) {
        return discountPolicy.getDiscountPrice(roughTotalPrice);
    }

    public long getServiceFee(long roughTotalPrice, long discountPrice) {
        return serviceFee.getFeePrice(roughTotalPrice - discountPrice);
    }

    public long getAccommodationFee(long serviceFeePrice) {
        return accommodationFee.getAccommodationFeePrice(serviceFeePrice);
    }

    public BookingInfo getBookingInfo(BookingRequestInfo requestInfo) {
        if (requestInfo.checkIn() == null || requestInfo.checkOut() == null || requestInfo.headCount() == null) {
            return BookingInfo.empty();
        }

        long roughTotalPrice = getRoughTotalPrice(requestInfo.avgPrice(), requestInfo.checkIn(), requestInfo.checkOut());
        long discountPrice = getDiscountPrice(roughTotalPrice);
        long serviceFee = getServiceFee(roughTotalPrice, discountPrice);
        long accommodationFee = getAccommodationFee(serviceFee);

        return BookingInfo.of(
                roughTotalPrice,
                discountPrice,
                serviceFee,
                accommodationFee
        );
    }
}

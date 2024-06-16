package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.transfer.BookingInfoForPriceInfo;
import team07.airbnb.service.booking.price_policy.fee.AccommodationFee;
import team07.airbnb.service.booking.price_policy.fee.ServiceFee;
import team07.airbnb.service.discount.DiscountPolicyService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class BookingPriceService {

    private final DiscountPolicyService discountPolicyService;
    private final ServiceFee serviceFee;
    private final AccommodationFee accommodationFee;

    public PriceInfo getPriceInfo(BookingInfoForPriceInfo requestInfo) {
        int roughTotalPrice = getRoughTotalPrice(requestInfo.avgPrice(), requestInfo.checkIn(), requestInfo.checkOut());
        int discountPrice = getDiscountPrice(roughTotalPrice);
        int serviceFee = getServiceFee(roughTotalPrice, discountPrice);
        int accommodationFee = getAccommodationFee(serviceFee);

        return PriceInfo.of(
                roughTotalPrice,
                discountPrice,
                serviceFee,
                accommodationFee
        );
    }

    public int getRoughTotalPrice(int avgPrice, LocalDate checkIn, LocalDate checkOut) {
        int days = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return avgPrice * days;
    }

    public int getDiscountPrice(int roughTotalPrice) {
        return discountPolicyService.getDiscountPrice(roughTotalPrice);
    }

    public int getServiceFee(int roughTotalPrice, int discountPrice) {
        return serviceFee.getFeePrice(roughTotalPrice - discountPrice);
    }

    public int getAccommodationFee(int serviceFeePrice) {
        return accommodationFee.getAccommodationFeePrice(serviceFeePrice);
    }
}

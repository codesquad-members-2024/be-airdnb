package team07.airbnb.domain.payment;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import team07.airbnb.domain.booking.dto.response.BookingInfo;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Fee {
    private long serviceFee;
    private long accommodationFee;

    public static Fee of(BookingInfo bookingInfo) {
        return new Fee(
                bookingInfo.getServiceFee(),
                bookingInfo.getAccommodationFee()
        );
    }
}

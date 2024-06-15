package team07.airbnb.data.booking.dto.transfer;

import jakarta.validation.constraints.NotNull;
import team07.airbnb.data.booking.dto.request.BookingPaymentsRequest;

public record DistanceInfo(

        Double longitude,

        Double latitude,

        Double distance
) {
    public static DistanceInfo of(BookingPaymentsRequest request) {
        return new DistanceInfo(
                request.longitude(),
                request.latitude(),
                request.distance()
        );
    }
}

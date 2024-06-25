package team07.airbnb.data.booking.dto.response;

import java.util.List;

public record BookingOverviewResponse(
        List<BookingDetailResponse> bookings,
        Integer totalIncome
) {

    public static BookingOverviewResponse of(List<BookingDetailResponse> bookings) {
        return new BookingOverviewResponse(
                bookings,
                bookings.stream()
                        .mapToInt(BookingDetailResponse::price)
                        .sum()
        );
    }
}

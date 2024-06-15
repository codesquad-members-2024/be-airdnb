package team07.airbnb.exception.bad_request;

import team07.airbnb.data.booking.enums.BookingStatus;

public class IllegalBookingStatusException extends BadRequestException{

    public IllegalBookingStatusException(BookingStatus status) {
        super("확정할 수 없는 예약입니다", "현재 예약 상태 : {%s}".formatted(status.toString()));
    }
}

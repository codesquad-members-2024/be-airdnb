package team07.airbnb.exception.bad_request;

import team07.airbnb.data.booking.dto.request.CreateBookingRequest;

public class InvalidBookingRequestException extends BadRequestException{

    public InvalidBookingRequestException(CreateBookingRequest bookingInfoForPriceInfo) {
        super("예약 생성에 필요한 정보가 충분하지 않습니다", bookingInfoForPriceInfo.toString());
    }
}

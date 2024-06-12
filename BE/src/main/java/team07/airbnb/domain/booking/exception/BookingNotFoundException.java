package team07.airbnb.domain.booking.exception;

import team07.airbnb.common.exception.NotFoundException;

public class BookingNotFoundException extends NotFoundException {

    public BookingNotFoundException(Long id) {
        super("해당 숙소를 찾을 수 없습니다", "{%d}인 숙소를 찾을 수 없습니다".formatted(id));
    }
}

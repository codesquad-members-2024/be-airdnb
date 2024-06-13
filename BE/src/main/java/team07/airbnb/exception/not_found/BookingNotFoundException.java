package team07.airbnb.exception.not_found;

public class BookingNotFoundException extends NotFoundException {

    public BookingNotFoundException(Long id) {
        super("해당 예약을 찾을 수 없습니다", "id : {%d}인 예약을 찾을 수 없습니다".formatted(id));
    }
}

package team07.airbnb.exception.not_found;

public class BookingNotFoundException extends NotFoundException {

    public BookingNotFoundException(Long id) {
        super("해당 숙소를 찾을 수 없습니다", "{%d}인 숙소를 찾을 수 없습니다".formatted(id));
    }
}

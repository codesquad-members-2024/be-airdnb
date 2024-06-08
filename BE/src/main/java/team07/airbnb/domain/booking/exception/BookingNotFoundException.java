package team07.airbnb.domain.booking.exception;

public class BookingNotFoundException extends RuntimeException{

    public BookingNotFoundException(Long id) {
        super("[ id -> %d ] 인 예약을 찾을 수 없습니다!".formatted(id));
    }
}

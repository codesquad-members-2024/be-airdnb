package team07.airbnb.domain.booking.exception;

public class InvalidDateException extends RuntimeException{

    public InvalidDateException() {
        super("날짜가 잘못 지정되었습니다!");
    }
}

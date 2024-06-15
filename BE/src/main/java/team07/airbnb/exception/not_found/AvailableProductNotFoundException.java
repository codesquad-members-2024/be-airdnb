package team07.airbnb.exception.not_found;

import java.time.LocalDate;

public class AvailableProductNotFoundException extends NotFoundException{

    public AvailableProductNotFoundException(LocalDate checkIn, LocalDate checkOut, Integer headCount) {
        super("예약 가능한 숙소가 존재하지 않습니다",
                "checkIn : {%s}, checkOut : {%s}, 인원수 : {%d}에 가능한 상품이 존재하지 않음".formatted(checkIn.toString(), checkOut.toString(), headCount));
    }
}

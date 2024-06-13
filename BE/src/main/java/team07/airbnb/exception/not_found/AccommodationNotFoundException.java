package team07.airbnb.exception.not_found;

public class AccommodationNotFoundException extends NotFoundException{
    public AccommodationNotFoundException(Long id) {
        super("해당 숙소를 찾을 수 없습니다", "id : {%d}인 숙소를 찾을 수 없습니다".formatted(id));
    }
}

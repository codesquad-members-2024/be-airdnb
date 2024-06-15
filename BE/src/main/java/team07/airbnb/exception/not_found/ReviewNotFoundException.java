package team07.airbnb.exception.not_found;

public class ReviewNotFoundException extends NotFoundException{

    public ReviewNotFoundException(Long id) {
        super("해당 리뷰를 찾을 수 없습니다", "{%d}인 리뷰를 찾을 수 없습니다".formatted(id));
    }
}

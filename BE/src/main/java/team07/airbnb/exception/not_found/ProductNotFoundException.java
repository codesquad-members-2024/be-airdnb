package team07.airbnb.exception.not_found;

public class ProductNotFoundException extends NotFoundException{

    public ProductNotFoundException(Long id) {
        super("해당 상품을 찾을 수 없습니다", "{%d}인 상품을 찾을 수 없습니다".formatted(id));
    }
}

package team07.airbnb.data.product.dto.response;

import team07.airbnb.entity.ProductEntity;

import java.time.LocalDate;

public record SimpleProductResponse (
        LocalDate date,
        Integer price
){
    public static SimpleProductResponse of(ProductEntity product){
        return new SimpleProductResponse(
                product.getDate(),
                product.getPrice()
        );
    }
}

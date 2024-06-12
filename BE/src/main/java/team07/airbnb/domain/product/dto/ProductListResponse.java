package team07.airbnb.domain.product.dto;

import team07.airbnb.domain.accommodation.dto.AccommodationListResponse;
import team07.airbnb.entity.ProductEntity;

public record ProductListResponse (
        AccommodationListResponse accomodation,
        int price
){

    public static ProductListResponse of(ProductEntity product){
        return new ProductListResponse(AccommodationListResponse.of(product.getAccommodation()), product.getPrice());
    }
}

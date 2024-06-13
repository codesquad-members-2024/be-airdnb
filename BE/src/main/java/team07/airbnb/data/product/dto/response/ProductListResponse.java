package team07.airbnb.data.product.dto.response;

import team07.airbnb.data.accommodation.dto.response.AccommodationListResponse;
import team07.airbnb.entity.ProductEntity;

public record ProductListResponse (
        AccommodationListResponse accommodation,
        int price
) {

    public static ProductListResponse of(ProductEntity product) {
        return new ProductListResponse(AccommodationListResponse.of(product.getAccommodation()), product.getPrice());
    }
}

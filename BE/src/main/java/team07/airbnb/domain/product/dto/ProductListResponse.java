package team07.airbnb.domain.product.dto;

import team07.airbnb.domain.accommodation.dto.AccommodationListResponse;

public record ProductListResponse (
        AccommodationListResponse accomodation,
        int price
){
}

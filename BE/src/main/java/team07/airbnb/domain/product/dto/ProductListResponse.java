package team07.airbnb.domain.product.dto;

import team07.airbnb.domain.accommodation.dto.AccomodationListResponse;

public record ProductListResponse (
        AccomodationListResponse accomodation,
        int price
){
}

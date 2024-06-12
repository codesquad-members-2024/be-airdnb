package team07.airbnb.domain.user.dto;

import team07.airbnb.domain.product.dto.ProductListResponse;

import java.util.List;

public record FavoritesResponse(
        List<ProductListResponse> available,
        List<ProductListResponse> nonAvailable
) {
}

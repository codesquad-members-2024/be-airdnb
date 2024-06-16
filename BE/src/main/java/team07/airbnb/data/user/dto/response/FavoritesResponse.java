package team07.airbnb.data.user.dto.response;

import team07.airbnb.data.product.dto.response.ProductListResponse;

import java.util.List;

public record FavoritesResponse(
        List<ProductListResponse> available,
        List<ProductListResponse> nonAvailable
) {
}

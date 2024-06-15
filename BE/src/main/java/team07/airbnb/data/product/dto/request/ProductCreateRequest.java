package team07.airbnb.data.product.dto.request;

import java.time.LocalDate;

public record ProductCreateRequest(
        long accommodationId,
        LocalDate date,
        Integer price
) {
}

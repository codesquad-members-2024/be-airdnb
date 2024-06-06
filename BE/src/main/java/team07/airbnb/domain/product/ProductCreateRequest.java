package team07.airbnb.domain.product;

import java.time.LocalDate;

public record ProductCreateRequest(
        long accommodationId,
        LocalDate date,
        Integer price
) {
}

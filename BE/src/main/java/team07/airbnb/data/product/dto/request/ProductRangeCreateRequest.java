package team07.airbnb.data.product.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.LocalDate;

public record ProductRangeCreateRequest (
        @Positive
        long accommodationId,

        @NotNull
        @FutureOrPresent
        LocalDate startDate,

        @NotNull
        @FutureOrPresent
        LocalDate endDate,

        @NotNull
        @PositiveOrZero
        Integer price
){}

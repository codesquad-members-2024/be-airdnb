package team07.airbnb.data.review.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record ReviewPostRequest(
        String content,

        @Min(0) @Max(5)
        int rating
) {
}

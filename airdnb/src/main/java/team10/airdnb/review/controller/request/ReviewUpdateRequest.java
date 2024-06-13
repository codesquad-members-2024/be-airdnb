package team10.airdnb.review.controller.request;

import jakarta.validation.constraints.NotNull;

public record ReviewUpdateRequest(
        String comment,
        @NotNull
        Double rate
) {
}

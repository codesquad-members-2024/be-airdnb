package team10.airdnb.payment.controller.request;

import jakarta.validation.constraints.NotBlank;

public record PaymentRequest(
        @NotBlank
        String paymentId,
        @NotBlank
        Long reservationId
) {
}

package team10.airdnb.payment.controller.response;

import lombok.Getter;

import java.math.BigDecimal;

public record PaymentResponse(
        String status,
        Amount amount
) {
    @Getter
    public static class Amount {
        private BigDecimal total;
        // getters and setters

    }
}

package team10.airdnb.payment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.payment.controller.request.PaymentRequest;
import team10.airdnb.payment.service.PaymentService;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payment/complete")
    public ResponseEntity<?> completePayment(@RequestBody PaymentRequest paymentRequest) {
        paymentService.completePayment(paymentRequest);

        return ResponseEntity.ok().build();
    }
}

package team10.airdnb.payment.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import team10.airdnb.payment.controller.request.PaymentRequest;
import team10.airdnb.payment.controller.response.PaymentResponse;
import team10.airdnb.reservation.entity.Reservation;
import team10.airdnb.reservation.exception.ReservationIdNotFoundException;
import team10.airdnb.reservation.repository.ReservationRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {
    private static final String PORTONE_API_SECRET = "WHmSQChVzPz8jCLgMRPUVYwqkPc3e10IrlI97b5JlywoUowU1adN5aXoTw0q5YvFMUb6kdoaYDQEL7A6";
    private static boolean CONFIRMED = true;

    private final ReservationRepository reservationRepository;

    public Reservation completePayment(PaymentRequest paymentRequest) {

        String paymentId = paymentRequest.paymentId();
        Long reservationId = paymentRequest.reservationId();

        // 1. 포트원 결제내역 단건조회 API 호출
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "PortOne " + PORTONE_API_SECRET);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<PaymentResponse> paymentResponse = restTemplate.exchange(
                "https://api.portone.io/payments/" + paymentId,
                HttpMethod.GET,
                entity,
                PaymentResponse.class
        );

        if (!paymentResponse.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("Payment response error: " + paymentResponse.getBody());
        }

        PaymentResponse payment = paymentResponse.getBody();

        Reservation reservation = reservationRepository.findById(reservationId).orElseThrow(ReservationIdNotFoundException::new);

        if (reservation.getTotalPrice().equals(payment.amount().getTotal())) {   // 실제 DB에 저장된 금액과 결제된 금액이 같은 지 비교
            switch (payment.status()) {
                case "VIRTUAL_ACCOUNT_ISSUED":
                    // 가상 계좌가 발급된 상태
                    // 구현 x
                    break;
                case "PAID":    // 확정된 상태
                    reservation.updateConfirmStatus(CONFIRMED); // 상태를 confirmed 상태로 변경
                    reservationRepository.save(reservation);    // db에 반영
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected Payment Status : " + payment.status());
            }
        } else {
            // 결제 금액이 불일치하여 위/변조 시도가 의심됩니다.
            throw new RuntimeException("Payment amount mismatch");
        }

        return reservation;
    }
}

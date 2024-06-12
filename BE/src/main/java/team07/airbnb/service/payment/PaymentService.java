package team07.airbnb.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.repository.PaymentRepository;
import team07.airbnb.entity.PaymentEntity;
import team07.airbnb.repository.PaymentRepository;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentEntity createNewPayment(PriceInfo priceInfo) {
        return paymentRepository.save(PaymentEntity.of(priceInfo));
    }

}

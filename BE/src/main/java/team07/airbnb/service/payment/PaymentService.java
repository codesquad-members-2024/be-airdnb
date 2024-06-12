package team07.airbnb.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.booking.dto.BookingInfo;
import team07.airbnb.repository.PaymentRepository;
import team07.airbnb.entity.PaymentEntity;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentEntity createNewPayment(BookingInfo bookingInfo) {
        return paymentRepository.save(PaymentEntity.of(bookingInfo));
    }

}

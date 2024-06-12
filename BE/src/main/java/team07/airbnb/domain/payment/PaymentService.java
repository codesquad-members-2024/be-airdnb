package team07.airbnb.domain.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.domain.booking.dto.BookingInfo;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentEntity createNewPayment(BookingInfo bookingInfo) {
        return paymentRepository.save(PaymentEntity.of(bookingInfo));
    }

}

package team07.airbnb.service.payment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.request.CreateBookingRequest;
import team07.airbnb.repository.PaymentRepository;
import team07.airbnb.entity.PaymentEntity;
import team07.airbnb.repository.PaymentRepository;
import team07.airbnb.service.booking.BookingPriceService;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingPriceService bookingPriceService;

    public PaymentEntity createNewPaymentByRequest(CreateBookingRequest request) {
        PriceInfo priceInfo = bookingPriceService.getPriceInfo(
                request.accommodationId(),
                request.checkIn(),
                request.checkOut(),
                request.headCount()
        );
        return paymentRepository.save(PaymentEntity.of(priceInfo));
    }

}

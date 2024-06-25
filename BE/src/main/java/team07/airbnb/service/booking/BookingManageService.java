package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.booking.dto.response.BookingConfirmedResponse;
import team07.airbnb.data.booking.dto.request.CreateBookingRequest;
import team07.airbnb.data.booking.dto.response.BookingCreateResponse;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.PaymentEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.bad_request.IllegalBookingStatusException;
import team07.airbnb.repository.BookingRepository;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.payment.PaymentService;
import team07.airbnb.service.product.ProductService;

import static team07.airbnb.data.booking.enums.BookingStatus.CONFIRM;
import static team07.airbnb.data.booking.enums.BookingStatus.REQUESTED;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingManageService {

    private final BookingInquiryService bookingInquiryService;
    private final BookingPriceService bookingPriceService;

    private final PaymentService paymentService;
    private final AccommodationService accommodationService;
    private final ProductService productService;

    private final BookingRepository bookingRepository;

    public void reopenBooking(Long bookingId) {
        BookingEntity booking = bookingInquiryService.findByBookingId(bookingId);
        booking.completeBooking();
        productService.reopen(booking);
        bookingRepository.save(booking);
    }

    public BookingCreateResponse createBooking(CreateBookingRequest request, UserEntity booker) {
        BookingEntity booking = buildBookingByCreateRequest(request, booker);

        productService.setProductStatusBooked(
                request.accommodationId(),
                request.checkIn(),
                request.checkOut(),
                request.headCount(),
                booking
        );

        bookingRepository.save(booking);

        return new BookingCreateResponse(
                accommodationService.findById(request.accommodationId()).getName(),
                booking.getId(),
                request.checkIn(),
                request.checkOut(),
                request.headCount()
        );
    }

    private BookingEntity buildBookingByCreateRequest(CreateBookingRequest request, UserEntity booker) {
        PaymentEntity payment = paymentService.createNewPaymentByRequest(request);

        UserEntity host = accommodationService.getHostIdById(request.accommodationId());

        return BookingEntity.builder()
                .host(host)
                .booker(booker)
                .checkin(request.checkIn())
                .checkout(request.checkOut())
                .headCount(request.headCount())
                .payment(payment)
                .status(REQUESTED)
                .build();
    }


    public BookingConfirmedResponse confirmBooking(Long bookingId, UserEntity requestedHost) {
        BookingEntity booking = bookingInquiryService.findByBookingId(bookingId);

        if (!booking.getHost().equals(requestedHost)) {
            throw new UnAuthorizedException(BookingInquiryService.class, requestedHost.getId(), "숙소의 호스트가 아닌 사람의 예약 확정 요청");
        }

        if (!booking.getStatus().equals(REQUESTED)) {
            throw new IllegalBookingStatusException(booking.getStatus());
        }

        booking.confirmBooking();
        return BookingConfirmedResponse.of(bookingRepository.save(booking));
    }

    public Integer cancelBooking(Long bookingId, UserEntity booker) {
        BookingEntity booking = bookingInquiryService.findByBookingId(bookingId);

        if (!booking.getBooker().equals(booker)) {
            throw new UnAuthorizedException(BookingInquiryService.class, booking.getId(), "예약의 당사자가 아닌 사람의 예약 취소 요청");
        }

        if (!(booking.getStatus() == REQUESTED || booking.getStatus() == CONFIRM)) {
            throw new IllegalBookingStatusException(booking.getStatus());
        }

        booking.cancelBooking();
        booking.getProducts().forEach(product -> product.open(booking));

        BookingEntity canceledBooking = bookingRepository.save(booking);

        return (int) (canceledBooking.getPayment().getTotalPrice() * (0.1));
    }

}

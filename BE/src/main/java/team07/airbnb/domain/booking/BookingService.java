package team07.airbnb.domain.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.domain.accommodation.AccommodationService;
import team07.airbnb.domain.booking.dto.request.BookingRequest;
import team07.airbnb.domain.booking.dto.BookingInfo;
import team07.airbnb.domain.booking.dto.response.BookingManageInfoResponse;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.domain.booking.exception.BookingNotFoundException;
import team07.airbnb.domain.booking.exception.DateInversionException;
import team07.airbnb.domain.booking.price_policy.fee.AccommodationFee;
import team07.airbnb.domain.booking.price_policy.fee.ServiceFee;
import team07.airbnb.domain.booking.property.BookingStatus;
import team07.airbnb.domain.discount.DiscountPolicyService;
import team07.airbnb.entity.PaymentEntity;
import team07.airbnb.domain.payment.PaymentService;
import team07.airbnb.domain.product.ProductService;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.UserEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DiscountPolicyService discountPolicyService;
    private final PaymentService paymentService;
    private final AccommodationService accommodationService;
    private final ProductService productService;

    private final ServiceFee serviceFee;
    private final AccommodationFee accommodationFee;

    @Transactional
    public BookingEntity createBooking(BookingInfo bookingInfo, BookingRequest request, UserEntity booker) throws IllegalStateException{
        PaymentEntity payment = paymentService.createNewPayment(bookingInfo);
        LocalDate checkIn = request.checkIn();
        LocalDate checkOut = request.checkOut();
        Integer headCount = request.headCount();

        UserEntity host = accommodationService.getHostIdById(request.accommodationId());

        BookingEntity booking = BookingEntity.builder()
                .host(host)
                .booker(booker)
                .checkin(checkIn)
                .checkout(checkOut)
                .headCount(headCount)
                .payment(payment)
                .status(BookingStatus.REQUESTED)
                .build();

        productService.getInDateRangeOfAccommodation(request.accommodationId(), checkIn, checkOut)
                .forEach(product -> product.book(booking));

        return bookingRepository.save(booking);
    }

    @Transactional
    public BookingEntity confirmBooking(BookingEntity booking, UserEntity requestedHost) {
        if (!booking.getStatus().equals(BookingStatus.REQUESTED)) {
            throw new IllegalStateException("확정할 수 없는 예약입니다");
        }

        if (!booking.getHost().equals(requestedHost)) {
            throw new IllegalStateException("예약의 호스트가 아닙니다");
        }

        booking.setStatus(BookingStatus.CONFIRM);

        return bookingRepository.save(booking);
    }

    @Transactional
    public BookingEntity cancelBooking(BookingEntity booking, UserEntity booker) {
        if (!booking.getBooker().equals(booker)) {
            throw new IllegalStateException("예약한 본인만 취소할 수 있습니다");
        }

        booking.setStatus(BookingStatus.CANCEL);
        booking.getProducts().forEach(product -> product.book(booking));

        return bookingRepository.save(booking);
    }

    public BookingInfo getBookingInfo(BookingRequest requestInfo) {
        if (requestInfo.checkIn() == null || requestInfo.checkOut() == null || requestInfo.headCount() == null) {
            return BookingInfo.empty();
        }

        long roughTotalPrice = getRoughTotalPrice(requestInfo.avgPrice(), requestInfo.checkIn(), requestInfo.checkOut());
        long discountPrice = getDiscountPrice(roughTotalPrice);
        long serviceFee = getServiceFee(roughTotalPrice, discountPrice);
        long accommodationFee = getAccommodationFee(serviceFee);

        return BookingInfo.of(
                roughTotalPrice,
                discountPrice,
                serviceFee,
                accommodationFee
        );
    }

    public void addReview(long bookingId, long writerId, ReviewEntity review) {
        BookingEntity booking = getById(bookingId);
        System.out.println(booking.getBooker().getId());
        System.out.println(writerId);
        if (booking.getBooker().getId() != writerId) throw new IllegalArgumentException("%d 번 예약의 예약자만 리뷰를 작성할 수 있습니다!".formatted(bookingId));

        bookingRepository.save(booking.addReview(review));
    }

    public BookingEntity findByBookingId(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException(id));
    }

    public List<BookingManageInfoResponse> getBookingInfoListByHostId(UserEntity host) {
        return bookingRepository.findAllByHost(host).stream()
                        .map(BookingManageInfoResponse::of)
                        .collect(Collectors.toList());
    }

    public boolean isRequestedUserSameInBooking(Long bookingId, UserEntity user) {
        return bookingRepository.existsByIdAndBooker(bookingId, user);
    }

    public boolean isRequestedHostSameInBooking(Long bookingId, UserEntity host) {
        return bookingRepository.existsByIdAndHost(bookingId, host);
    }

    public long getRoughTotalPrice(long avgPrice, LocalDate checkIn, LocalDate checkOut) {
        long days = ChronoUnit.DAYS.between(checkIn, checkOut);
        if (days <= 0) throw new DateInversionException(checkIn, checkOut);

        return avgPrice * days;
    }
    public long getDiscountPrice(long roughTotalPrice) {
        return discountPolicyService.getDiscountPrice(roughTotalPrice);
    }

    public long getServiceFee(long roughTotalPrice, long discountPrice) {
        return serviceFee.getFeePrice(roughTotalPrice - discountPrice);
    }

    public long getAccommodationFee(long serviceFeePrice) {
        return accommodationFee.getAccommodationFeePrice(serviceFeePrice);
    }

    private BookingEntity getById(long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new NoSuchElementException("%d 번 예약이 존재하지 않습니다!".formatted(bookingId)));
    }
}

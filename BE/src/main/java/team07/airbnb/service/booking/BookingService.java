package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.booking.dto.response.BookingCreateResponse;
import team07.airbnb.data.booking.dto.transfer.BookingInfoForPriceInfo;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.bad_request.IllegalBookingStatusException;
import team07.airbnb.exception.bad_request.InvalidBookingRequestException;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.repository.BookingRepository;
import team07.airbnb.data.booking.dto.request.BookingRequest;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.response.BookingManageInfoResponse;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.exception.not_found.BookingNotFoundException;
import team07.airbnb.exception.bad_request.DateInversionException;
import team07.airbnb.service.booking.price_policy.fee.AccommodationFee;
import team07.airbnb.service.booking.price_policy.fee.ServiceFee;
import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.service.discount.DiscountPolicyService;
import team07.airbnb.entity.PaymentEntity;
import team07.airbnb.service.payment.PaymentService;
import team07.airbnb.service.product.ProductService;
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
    public BookingCreateResponse createBooking(BookingInfoForPriceInfo bookingInfo, Long accId, UserEntity booker) {
        PriceInfo priceInfo = getPriceInfo(bookingInfo);
        if (priceInfo.isEmpty()) {
            throw new InvalidBookingRequestException(bookingInfo);
        }

        PaymentEntity payment = paymentService.createNewPayment(priceInfo);
        LocalDate checkIn = bookingInfo.checkIn();
        LocalDate checkOut = bookingInfo.checkOut();
        Integer headCount = bookingInfo.headCount();

        UserEntity host = accommodationService.getHostIdById(accId);

        BookingEntity booking = BookingEntity.builder()
                .host(host)
                .booker(booker)
                .checkin(checkIn)
                .checkout(checkOut)
                .headCount(headCount)
                .payment(payment)
                .status(BookingStatus.REQUESTED)
                .build();

        productService.getInDateRangeOfAccommodation(accId, checkIn, checkOut)
                .forEach(product -> product.book(booking));

        String accName = accommodationService.findById(accId).getName();

        return new BookingCreateResponse(accName, booking.getId(), checkIn, checkOut, headCount);
    }

    @Transactional
    public Long confirmBooking(Long bookingId, UserEntity requestedHost) {
        BookingEntity booking = findByBookingId(bookingId);

        if (!booking.getStatus().equals(BookingStatus.REQUESTED)) {
            throw new IllegalBookingStatusException(booking.getStatus());
        }

        if (!booking.getHost().equals(requestedHost)) {
            throw new UnAuthorizedException(BookingService.class, requestedHost.getId(), "숙소의 호스트가 아닌 사람의 숙소 확인 요청");
        }

        booking.confirmBooking();

        return bookingRepository.save(booking).getId();
    }

    @Transactional
    public Long cancelBooking(Long bookingId, UserEntity booker) {
        BookingEntity booking = findByBookingId(bookingId);
        if (!booking.getBooker().equals(booker)) {
            throw new UnAuthorizedException(BookingService.class, booking.getId(), "예약의 당사자가 아닌 사람의 예약 취소 요청");
        }

        booking.cancelBooking();
//        booking.getProducts().forEach(product -> product.book(booking));

        BookingEntity canceledBooking = bookingRepository.save(booking);

        return (long) (canceledBooking.getPayment().getTotalPrice() * (0.1));
    }

    public PriceInfo getPriceInfo(BookingInfoForPriceInfo requestInfo) {
        if (requestInfo.checkIn() == null || requestInfo.checkOut() == null || requestInfo.headCount() == null) {
            return PriceInfo.empty();
        }

        long roughTotalPrice = getRoughTotalPrice(requestInfo.avgPrice(), requestInfo.checkIn(), requestInfo.checkOut());
        long discountPrice = getDiscountPrice(roughTotalPrice);
        long serviceFee = getServiceFee(roughTotalPrice, discountPrice);
        long accommodationFee = getAccommodationFee(serviceFee);

        return PriceInfo.of(
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

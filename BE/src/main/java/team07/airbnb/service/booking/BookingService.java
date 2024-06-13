package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.response.BookingCreateResponse;
import team07.airbnb.data.booking.dto.response.BookingDetailResponse;
import team07.airbnb.data.booking.dto.response.BookingManageInfoResponse;
import team07.airbnb.data.booking.dto.transfer.BookingInfoForPriceInfo;
import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.PaymentEntity;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.bad_request.IllegalBookingStatusException;
import team07.airbnb.exception.bad_request.InvalidBookingRequestException;
import team07.airbnb.exception.not_found.BookingNotFoundException;
import team07.airbnb.repository.BookingRepository;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.booking.price_policy.fee.AccommodationFee;
import team07.airbnb.service.booking.price_policy.fee.ServiceFee;
import team07.airbnb.service.discount.DiscountPolicyService;
import team07.airbnb.service.payment.PaymentService;
import team07.airbnb.service.product.ProductService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static team07.airbnb.data.booking.enums.BookingStatus.CONFIRM;
import static team07.airbnb.data.booking.enums.BookingStatus.REQUESTED;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DiscountPolicyService discountPolicyService;
    private final PaymentService paymentService;
    private final AccommodationService accommodationService;
    private final ProductService productService;

    private final ServiceFee serviceFee;
    private final AccommodationFee accommodationFee;

    /**
     * 정오마다 오늘이 체크아웃 날짜인 모든 예약을 이용 완료 상태로 변경
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void completeAllBookings() {
        LocalDate now = LocalDate.now();

        List<BookingEntity> bookings = bookingRepository.findAllByCheckout(now);

        bookings.forEach(booking -> {
            booking.setStatus(BookingStatus.COMPLETE);
            booking.getProducts().stream().close();
        });
    }

    public void reopenBooking(Long bookingId) {
        BookingEntity booking = findByBookingId(bookingId);
        booking.completeBooking();
        productService.reopen(booking);
        bookingRepository.save(booking);
    }

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
                .status(REQUESTED)
                .build();

        productService.getInDateRangeOfAccommodation(accId, checkIn, checkOut, headCount)
                .forEach(product -> product.book(booking));

        String accName = accommodationService.findById(accId).getName();

        return new BookingCreateResponse(accName, booking.getId(), checkIn, checkOut, headCount);
    }

    public Long confirmBooking(Long bookingId, UserEntity requestedHost) {
        BookingEntity booking = findByBookingId(bookingId);

        if (!booking.getHost().equals(requestedHost)) {
            throw new UnAuthorizedException(BookingService.class, requestedHost.getId(), "숙소의 호스트가 아닌 사람의 예약 확정 요청");
        }

        if (!booking.getStatus().equals(REQUESTED)) {
            throw new IllegalBookingStatusException(booking.getStatus());
        }


        booking.confirmBooking();

        return bookingRepository.save(booking).getId();
    }

    public Integer cancelBooking(Long bookingId, UserEntity booker) {
        BookingEntity booking = findByBookingId(bookingId);

        if (!booking.getBooker().equals(booker)) {
            throw new UnAuthorizedException(BookingService.class, booking.getId(), "예약의 당사자가 아닌 사람의 예약 취소 요청");
        }

        if (!(booking.getStatus() == REQUESTED || booking.getStatus() == CONFIRM)) {
            throw new IllegalBookingStatusException(booking.getStatus());
        }

        booking.cancelBooking();
        booking.getProducts().forEach(product -> product.open(booking));

        BookingEntity canceledBooking = bookingRepository.save(booking);

        return (int) (canceledBooking.getPayment().getTotalPrice() * (0.1));
    }

    public PriceInfo getPriceInfo(BookingInfoForPriceInfo requestInfo) {
        int roughTotalPrice = getRoughTotalPrice(requestInfo.avgPrice(), requestInfo.checkIn(), requestInfo.checkOut());
        int discountPrice = getDiscountPrice(roughTotalPrice);
        int serviceFee = getServiceFee(roughTotalPrice, discountPrice);
        int accommodationFee = getAccommodationFee(serviceFee);

        return PriceInfo.of(
                roughTotalPrice,
                discountPrice,
                serviceFee,
                accommodationFee
        );
    }

    public List<BookingDetailResponse> findBookingsByUser(UserEntity booker) {
        return bookingRepository.findAllByBooker(booker).stream().map(BookingDetailResponse::of).toList();
    }

    public void addReview(Long bookingId, Long writerId, ReviewEntity review) {
        BookingEntity booking = findByBookingId(bookingId);
        if (!booking.getBooker().getId().equals(writerId))
            throw new UnAuthorizedException(BookingService.class, writerId, "%d 번 예약의 예약자만 리뷰를 작성할 수 있습니다!".formatted(bookingId));

        bookingRepository.save(booking.addReview(review));
    }

    public BookingEntity findByBookingId(Long id) {
        return bookingRepository.findById(id).orElseThrow(() -> new BookingNotFoundException(id));
    }

    public List<BookingDetailResponse> getBookingInfoListByHost(UserEntity host) {
        return bookingRepository.findAllByHost(host).stream()
                .map(BookingDetailResponse::of)
                .toList();
    }

    public boolean isRequestedHostNotMatchInBooking(Long bookingId, UserEntity host) {
        return !bookingRepository.existsByIdAndHost(bookingId, host);
    }

    public boolean isUserHostOrBookerOf(Long bookingId , UserEntity user){
        return isUserHostOf(bookingId, user) || isUserBookerOf(bookingId, user);
    }

    public boolean isUserHostOf(Long bookingId, UserEntity booker) {
        return bookingRepository.existsByIdAndBooker(bookingId, booker);
    }

    public boolean isUserBookerOf(Long bookingId, UserEntity host) {
        return bookingRepository.existsByIdAndHost(bookingId, host);
    }

    public boolean isRequestedUserNotMatchInBooking(Long bookingId, UserEntity booker) {
        return !bookingRepository.existsByIdAndBooker(bookingId, booker);
    }

    public int getRoughTotalPrice(int avgPrice, LocalDate checkIn, LocalDate checkOut) {
        int days = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return avgPrice * days;
    }

    public int getDiscountPrice(int roughTotalPrice) {
        return discountPolicyService.getDiscountPrice(roughTotalPrice);
    }

    public int getServiceFee(int roughTotalPrice, int discountPrice) {
        return serviceFee.getFeePrice(roughTotalPrice - discountPrice);
    }

    public int getAccommodationFee(int serviceFeePrice) {
        return accommodationFee.getAccommodationFeePrice(serviceFeePrice);
    }
}

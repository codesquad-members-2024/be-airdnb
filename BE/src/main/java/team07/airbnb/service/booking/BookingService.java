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
import team07.airbnb.data.booking.dto.PriceInfo;
import team07.airbnb.data.booking.dto.response.BookingManageInfoResponse;
import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.PaymentEntity;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.not_found.BookingNotFoundException;
import team07.airbnb.exception.bad_request.DateInversionException;
import team07.airbnb.service.booking.price_policy.fee.AccommodationFee;
import team07.airbnb.service.booking.price_policy.fee.ServiceFee;
import team07.airbnb.service.discount.DiscountPolicyService;
import team07.airbnb.service.payment.PaymentService;
import team07.airbnb.service.product.ProductService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static team07.airbnb.data.booking.enums.BookingStatus.*;

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
            throw new UnAuthorizedException(BookingService.class, requestedHost.getId(), "숙소의 호스트가 아닌 사람의 숙소 확인 요청");
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
        if (requestInfo.checkIn() == null || requestInfo.checkOut() == null || requestInfo.headCount() == null) {
            return PriceInfo.empty();
        }

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

    public void addReview(Long bookingId, Long writerId, ReviewEntity review) {
        BookingEntity booking = getById(bookingId);
        if (!booking.getBooker().getId().equals(writerId)) throw new UnAuthorizedException(BookingService.class, writerId, "%d 번 예약의 예약자만 리뷰를 작성할 수 있습니다!".formatted(bookingId));

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

    public boolean isRequestedHostNotMatchInBooking(Long bookingId, UserEntity host) {
        return bookingRepository.existsByIdAndHost(bookingId, host);
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

    private BookingEntity getById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new NoSuchElementException("%d 번 예약이 존재하지 않습니다!".formatted(bookingId)));
    }
}

package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.controller.ReviewController;
import team07.airbnb.data.booking.dto.response.BookingDetailResponse;
import team07.airbnb.data.booking.dto.transfer.DateInfo;
import team07.airbnb.data.booking.dto.transfer.DistanceInfo;
import team07.airbnb.data.booking.enums.BookingStatus;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.ProductEntity;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.not_found.BookingNotFoundException;
import team07.airbnb.repository.BookingRepository;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.product.ProductService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingInquiryService {

    private final AccommodationService accommodationService;
    private final ProductService productService;

    private final BookingRepository bookingRepository;

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


    public List<BookingDetailResponse> findBookingsByUser(UserEntity booker) {
        return bookingRepository.findAllByBooker(booker).stream().map(BookingDetailResponse::of).toList();
    }

    public void addReview(Long bookingId, Long writerId, ReviewEntity review) {
        BookingEntity booking = findByBookingId(bookingId);
        if (!booking.getBooker().getId().equals(writerId))
            throw new UnAuthorizedException(ReviewController.class, writerId, "%d 번 예약의 예약자만 리뷰를 작성할 수 있습니다!".formatted(bookingId));

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

    public List<Double> getPricesAccAvailable(DateInfo dateInfo, DistanceInfo distanceInfo) {
        List<AccommodationEntity> nearbyAccommodations =
                accommodationService.findNearbyAccommodations(distanceInfo.longitude(), distanceInfo.latitude(), distanceInfo.distance());

        return nearbyAccommodations.stream()
                    .map(accommodationEntity -> productService.getInDateRangeOfAccommodation(accommodationEntity.getId(), dateInfo.checkIn(), dateInfo.checkOut(), null))
                    .map(productEntities -> productEntities.stream().mapToInt(ProductEntity::getPrice).average().orElseGet(() -> -1.0))
                    .toList();
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


}

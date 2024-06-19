package team07.airbnb.service.booking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.controller.ReviewController;
import team07.airbnb.data.booking.dto.response.BookingDetailResponse;
import team07.airbnb.data.booking.dto.transfer.DateInfo;
import team07.airbnb.data.booking.dto.transfer.DistanceInfo;
import team07.airbnb.data.review.dto.request.ReviewPostRequest;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookingInquiryService {

    private final AccommodationService accommodationService;
    private final ProductService productService;
    private final BookingRepository bookingRepository;


    public List<BookingDetailResponse> findBookingsByUser(UserEntity booker) {
        return bookingRepository.findAllByBooker(booker).stream().map(BookingDetailResponse::of).toList();
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
}

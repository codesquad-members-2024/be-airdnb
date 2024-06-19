package team07.airbnb.service.product;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.controller.AccommodationFilterDTO;
import team07.airbnb.data.accommodation.dto.response.AccommodationListResponse;
import team07.airbnb.data.product.dto.response.ProductListResponse;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.BookingEntity;
import team07.airbnb.entity.ProductEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.exception.IllegalRequestException;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.not_found.ProductNotFoundException;
import team07.airbnb.repository.ProductRepository;
import team07.airbnb.service.accommodation.AccommodationService;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final AccommodationService accommodationService;

    public List<ProductListResponse> findWithFilter(AccommodationFilterDTO filter) {

        List<AccommodationEntity> nearbyAccommodations = accommodationService.findNearbyAccommodations(
                filter.longitude(),
                filter.latitude(),
                filter.distance() * 1000);

        Stream<List<ProductEntity>> filteredStream = getFilteredStream(filter, nearbyAccommodations);

        List<ProductListResponse> accommodations = filteredStream
                .map(openProducts -> new ProductListResponse(
                                AccommodationListResponse.of(openProducts.get(0).getAccommodation()),
                                getAveragePriceForDateRange(openProducts, filter.checkInDate(), filter.checkOutDate())
                        )
                ).toList();

        if (filter.minPrice() != null){
            accommodations = accommodations.stream()
                    .filter(accommodation -> accommodation.price() >= filter.minPrice())
                    .toList();
        }

       if (filter.maxPrice() != null){
            accommodations = accommodations.stream()
                    .filter(accommodation -> accommodation.price() <= filter.maxPrice())
                    .toList();
        }

       return accommodations;
    }

    private Stream<List<ProductEntity>> getFilteredStream(AccommodationFilterDTO filter, List<AccommodationEntity> nearbyAccommodations) {
        Stream<List<ProductEntity>> accommodations = nearbyAccommodations.stream()
                .map(AccommodationEntity::getOpenProducts);




        if (filter.checkInDate() != null && filter.checkOutDate() != null) accommodations =
                accommodations.
                        filter(openProducts -> isAvailableInDateRange(
                                openProducts,
                                filter.checkInDate(),
                                filter.checkOutDate()));

        if(filter.headCount() != null) accommodations =
                accommodations
                .filter(openProducts -> isAvailablePeopleCount(openProducts, filter.headCount()));

        return accommodations;
    }

    private int getAveragePriceForDateRange(List<ProductEntity> openProducts, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkInDate != null && checkOutDate != null) {
            openProducts = openProducts.stream()
                    .filter(product -> product.getDate().isBefore(checkInDate) && !product.getDate().isAfter(checkOutDate))
                    .toList();
        }
        return (int) openProducts.stream()
                .mapToInt(ProductEntity::getPrice)
                .average()
                .getAsDouble();
    }


    public List<ProductEntity> getInDateRangeOfAccommodation(Long accommodationId, LocalDate checkIn, LocalDate checkOut, Integer headCount) {
        if (isIllegalRequest(accommodationId, checkIn, checkOut, headCount)) {
            throw new IllegalRequestException(ProductService.class);
        }

        return productRepository.findAllByAccommodationIdAndDateBetween(accommodationId, checkIn, checkOut);
    }

    public ProductEntity findById(long id) {
        return getProductById(id);
    }

    public void reopen(BookingEntity booking) {
        LocalDate checkOut = booking.getCheckout();

        //예약이 완료된 다음날부터 체크아웃 날짜까지만 상품 재생성
        List<ProductEntity> remainProducts = booking.getProducts().stream()
                .filter(product -> product.isDateInRange(LocalDate.now(), checkOut))
                .toList();

        remainProducts.forEach(product -> productRepository.save(product.reopen(booking)));
    }

    public void closeProduct(Long productId, Long userId) {
        ProductEntity product = authorize(productId, userId);
        product.close(null);
        productRepository.save(product);
    }

    public void updatePrice(Long productId, Integer price, Long userId) {
        ProductEntity product = authorize(productId, userId);
        product.setPrice(price);
        productRepository.save(product);
    }

    public void setProductStatusBooked(Long accId, LocalDate checkIn, LocalDate checkOut, Integer headCount, BookingEntity booking) {
        this.getInDateRangeOfAccommodation(accId, checkIn, checkOut, headCount)
                .forEach(product -> product.book(booking));
    }


    private ProductEntity getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    private ProductEntity authorize(Long id, Long userId) {
        ProductEntity product = getProductById(id);
        if (!product.getAccommodation().getHost().getId().equals(userId))
            throw new UnAuthorizedException(this.getClass(), userId, "ID : {%d} 호스트가 ID : {%d} 상품을 변경 시도함".formatted(userId, id));

        return product;
    }

    private boolean isIllegalRequest(Long accId, LocalDate checkIn, LocalDate checkOut, Integer headCount) {
        //예외 처리 로직(PostMan 같은걸로 웹 페이지가 아니라 데이터를 직접 보냈을때)
        AccommodationEntity accommodation = accommodationService.findById(accId);
        List<ProductEntity> products = accommodation.getOpenProducts();

        return !isAvailablePeopleCount(products, headCount) && !isAvailableInDateRange(products, checkIn, checkOut);
    }

    private boolean isAvailablePeopleCount(List<ProductEntity> products, Integer headCount) {
        //인원수가 1이면 무조건 true
        if (headCount == 1) return true;

        if (products.isEmpty()) return false;

        return accommodationService.isAvailableOccupancy(products.get(0).getAccommodation(), headCount);
    }

    private boolean isAvailableInDateRange(List<ProductEntity> products, LocalDate checkIn, LocalDate checkOut) {
        //날짜를 설정하지 않았으면 모든 날짜가 가능한걸로 취급
        if (checkIn == null && checkOut == null) return true;

        Set<LocalDate> dateSet = new HashSet<>();
        for (LocalDate date = checkIn; !date.isAfter(checkOut); date = date.plusDays(1)) {
            dateSet.add(date);
        }

        for (ProductEntity product : products) {
            dateSet.remove(product.getDate());
        }

        return dateSet.isEmpty();
    }

    public void createProduct(long accommodationId, LocalDate date, Integer requestPrice) {
        AccommodationEntity accommodation = accommodationService.findById(accommodationId);
        int price = requestPrice == null ? accommodation.getBasePricePerDay() : requestPrice;
        accommodation.addProduct(date, price);
    }
}

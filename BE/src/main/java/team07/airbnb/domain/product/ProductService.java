package team07.airbnb.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.accommodation.AccommodationService;
import team07.airbnb.domain.accommodation.dto.AccommodationListResponse;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.domain.product.dto.ProductListResponse;
import team07.airbnb.domain.product.entity.ProductEntity;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static team07.airbnb.domain.product.entity.ProductStatus.OPEN;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AccommodationService accommodationService;

    public List<ProductListResponse> findAvailableInDateRange(
            List<AccommodationEntity> accommodations, LocalDate checkIn, LocalDate checkOut, Integer headCount) {

        List<Long> accomodationIds = accommodations.stream().map(AccommodationEntity::getId).toList();

        Map<Long, List<ProductEntity>> availableProducts = getAvailableProducts(checkIn, checkOut, headCount, accomodationIds);

        // Calculate average price per day and Make Response List
        return availableProducts.keySet().stream().map(key -> {
                    List<ProductEntity> productEntities = availableProducts.get(key);
                    return new ProductListResponse(
                            AccommodationListResponse.of(productEntities.get(0).getAccommodation()),
                            (int) productEntities.stream().mapToInt(ProductEntity::getPrice).average().getAsDouble()
                    );
                }
        ).toList();
    }

    public Map<Long, List<ProductEntity>> getAvailableProducts(LocalDate checkIn, LocalDate checkOut, Integer headCount, List<Long> accomodationIds) {
        Map<Long, List<ProductEntity>> products = productRepository.findAllByAccommodationIdInAndStatus(accomodationIds, OPEN)
                .stream()
                .collect(Collectors.groupingBy(p -> p.getAccommodation().getId()));

        return products.entrySet().stream()
                .filter(entry -> isAvailableInDateRange(entry.getValue(), checkIn, checkOut) && isAvailablePeopleCount(entry.getValue(), headCount))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    private boolean isAvailablePeopleCount(List<ProductEntity> products, Integer headCount) {
        //인원수를 설정하지 않았으면 무조건 true
        if (headCount == null) return true;

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
}

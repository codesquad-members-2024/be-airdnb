package team07.airbnb.domain.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.accommodation.AccommodationService;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.domain.product.dto.ProductListResponse;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "상품")
@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final AccommodationService accommodationService;
    private final ProductService productService;

    @GetMapping("/available")
    public List<ProductListResponse> findNearByAvailableProducts(
            @RequestParam @Nullable LocalDate checkIn,
            @RequestParam @Nullable  LocalDate checkOut,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance,
            @RequestParam @Nullable Integer headCount
    ) {

        return productService.findAvailableInDateRange(
                accommodationService.findNearbyAccommodations(longitude, latitude, distance),
                checkIn, checkOut, headCount);
    }

    @PostMapping()
    public void createProduct(@RequestBody ProductCreateRequest request) {
        AccommodationEntity accommodation = accommodationService.findById(request.accommodationId());
        int price = request.price() == null ? accommodation.getBasePricePerDay() : request.price();

        accommodation.addProduct(request.date(), price);
    }
}

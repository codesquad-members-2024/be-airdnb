package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.data.product.dto.request.ProductCreateRequest;
import team07.airbnb.data.product.dto.response.ProductListResponse;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.product.ProductService;

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
    
    @Tag(name = "User")
    @Operation(summary = "주변 예약 가능 상품 조회")
    @GetMapping("/available")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductListResponse> findNearByAvailableProducts(
            @RequestParam @Nullable LocalDate checkIn,
            @RequestParam @Nullable LocalDate checkOut,
            @RequestParam Double longitude,
            @RequestParam Double latitude,
            @RequestParam Double distance,
            @RequestParam @Nullable Integer headCount
    ) {

        return productService.findAvailableInDateRange(
                accommodationService.findNearbyAccommodations(longitude, latitude, distance * 1000),
                checkIn, checkOut, headCount);
    }
    
    @Tag(name = "Host")
    @Operation(summary = "상품 생성")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductCreateRequest request) {
        AccommodationEntity accommodation = accommodationService.findById(request.accommodationId());
        int price = request.price() == null ? accommodation.getBasePricePerDay() : request.price();

        accommodation.addProduct(request.date(), price);
    }
}

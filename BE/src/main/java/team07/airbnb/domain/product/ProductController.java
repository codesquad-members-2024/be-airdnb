package team07.airbnb.domain.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.accommodation.AccomodationService;
import team07.airbnb.domain.product.dto.ProductListResponse;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
public class ProductController {

    private final AccomodationService accomodationService;
    private final ProductService productService;

    @GetMapping("/available")
    public List<ProductListResponse> findNearByAvailableProducts(
            @RequestParam LocalDate checkIn,
            @RequestParam LocalDate checkOut,
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance) {

        return productService.findAvailableInDateRange(
                accomodationService.findNearbyAccommodations(longitude, latitude, distance),
                checkIn, checkOut);
    }
}

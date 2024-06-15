package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.product.dto.request.ProductCreateRequest;
import team07.airbnb.data.product.dto.response.ProductListResponse;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.product.ProductService;
import team07.airbnb.service.user.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static team07.airbnb.data.user.enums.Role.*;

@Tag(name = "상품")
@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final AccommodationService accommodationService;
    private final ProductService productService;
    private final UserService userService;

    @Tag(name = "User")
    @Operation(summary = "주변 예약 가능 상품 조회")
    @GetMapping("/available")
    @ResponseStatus(OK)
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
    @ResponseStatus(CREATED)
    public void createProduct(@RequestBody ProductCreateRequest request) {
        AccommodationEntity accommodation = accommodationService.findById(request.accommodationId());
        int price = request.price() == null ? accommodation.getBasePricePerDay() : request.price();

        accommodation.addProduct(request.date(), price);
    }

    @Tag(name = "Host")
    @Operation(summary = "상품 닫기")
    @PostMapping("/close/{productId}")
    @ResponseStatus(OK)
    @Authenticated(HOST)
    public void closeProduct(@PathVariable @NotNull Long productId, TokenUserInfo hostInfo) {
        if (!productService.isHostOf(productId, userService.getCompleteUser(hostInfo))) {
            throw new UnAuthorizedException(ProductController.class, hostInfo.id(), "ID : {%d} 호스트가 ID : {%d} 상품을 닫으려고 시도함".formatted(hostInfo.id(), productId));
        }

        productService.closeProduct(productId);
    }

    @PostMapping("/update/{productId}")
    @ResponseStatus(OK)
    @Authenticated(HOST)
    public void updatePrice(@PathVariable @NotNull Long productId,
                            @RequestParam @NotNull @Size Integer price,
                            TokenUserInfo hostInfo) {
        if (!productService.isHostOf(productId, userService.getCompleteUser(hostInfo))) {
            throw new UnAuthorizedException(ProductController.class, hostInfo.id(), "ID : {%d} 호스트가 ID : {%d} 상품의 가격수정을 시도함".formatted(hostInfo.id(), productId));
        }

        productService.updatePrice(productId, price);
    }
}

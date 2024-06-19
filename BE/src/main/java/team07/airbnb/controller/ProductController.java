package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.product.dto.request.ProductCreateRequest;
import team07.airbnb.data.product.dto.response.ProductListResponse;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.service.product.ProductService;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static team07.airbnb.data.user.enums.Role.HOST;

@Tag(name = "상품")
@RequestMapping("/products")
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Tag(name = "User")
    @Operation(summary = "주변 예약 가능 상품 필터링 조회 ")
    @GetMapping("/available")
    @ResponseStatus(OK)
    public List<ProductListResponse> findNearByAvailableProducts(
            @RequestParam AccommodationFilterDTO filter
    ) {
        return productService.findWithFilter(filter);
    }

    @Tag(name = "Host")
    @Operation(summary = "상품 생성")
    @PostMapping()
    @ResponseStatus(CREATED)
    public void createProduct(@RequestBody ProductCreateRequest request) {
        productService.createProduct(request.accommodationId(), request.date(), request.price());
    }

    @Tag(name = "Host")
    @Operation(summary = "상품 닫기")
    @PostMapping("/close/{productId}")
    @ResponseStatus(OK)
    @Authenticated(HOST)
    public void closeProduct(@PathVariable @NotNull Long productId, UserEntity hostInfo) {

        productService.closeProduct(productId, hostInfo.getId());
    }

    @PostMapping("/update/{productId}")
    @ResponseStatus(OK)
    @Authenticated(HOST)
    public void updatePrice(@PathVariable @NotNull Long productId,
                            @RequestParam @NotNull @Size Integer price,
                            UserEntity hostInfo) {

        productService.updatePrice(productId, price, hostInfo.getId());
    }
}

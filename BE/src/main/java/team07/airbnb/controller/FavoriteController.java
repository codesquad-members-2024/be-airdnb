package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.product.ProductStatus;
import team07.airbnb.data.product.dto.response.ProductListResponse;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.data.user.dto.response.FavoritesResponse;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.LikeEntity;
import team07.airbnb.entity.ProductEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.service.product.ProductService;
import team07.airbnb.service.user.UserService;

import java.util.ArrayList;
import java.util.List;

@Tag(name = "위시리스트")
@RestController
@RequiredArgsConstructor
@RequestMapping("/favorite")
public class FavoriteController {

    private final UserService userService;
    private final ProductService productService;

    @Tag(name = "User")
    @Operation(summary = "위시리스트에 상품 추가")
    @Authenticated(Role.USER)
    @PostMapping("/{id}")
    public void addFavorite(@PathVariable long id, UserEntity user) {
        userService.addFavorite(user.getId(), productService.findById(id));
    }

    @Tag(name = "User")
    @Operation(summary = "위시리스트에서 상품 삭제")
    @Authenticated(Role.USER)
    @DeleteMapping("/{id}")
    public void removeFavorite(@PathVariable long id, UserEntity user) {
        userService.removeFavorite(user.getId(), productService.findById(id));

    }

    @Tag(name = "User")
    @Operation(summary = "내 위시리스트 조회")
    @Authenticated(Role.USER)
    @GetMapping("my")
    public FavoritesResponse getMyWishList(UserEntity user) {
        List<ProductEntity> available = new ArrayList<>();
        List<ProductEntity> nonAvailable = new ArrayList<>();

        for (LikeEntity like : user.getFavorites()) {
            ProductEntity product = like.getProduct();
            if (product.getStatus() == ProductStatus.OPEN) available.add(product);
            else nonAvailable.add(product);
        }

        return new FavoritesResponse(
                available.stream().map(ProductListResponse::of).toList(),
                nonAvailable.stream().map(ProductListResponse::of).toList()
        );
    }
}

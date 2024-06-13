package com.team01.airdnb.wishlist;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.wishlist.dto.WishlistCreateRequest;
import com.team01.airdnb.wishlist.dto.WishlistListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class WishlistController {

  private final WishlistService wishlistService;

  @PostMapping("/wishlists")
  public void createWishlist(@RequestBody WishlistCreateRequest wishlistCreateRequest) {
    wishlistService.createWishList(wishlistCreateRequest);
  }

  @GetMapping("/wishlists/{userId}")
  public List<WishlistListResponse> findWishlistList(@PathVariable String userId) {
    //로그인 유저 정보와 파라미터 유저 정보가 같은지 체크
    return wishlistService.wishlistList(userId);
  }

  @DeleteMapping("/wishlists/{wishlistId}")
  public void deleteWishlist(@PathVariable Long wishlistId) {
    //wishlist 등록된 유저 아이디와 로그인한 아이디가 같은지 검증
    wishlistService.deleteWishlist(wishlistId);
  }

}

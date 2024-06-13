package com.team01.airdnb.wishlist;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.accommadation.AccommodationRepository;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserRepository;
import com.team01.airdnb.wishlist.dto.WishlistCreateRequest;
import com.team01.airdnb.wishlist.dto.WishlistListResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WishlistService {
  private final WishlistRepository wishlistRepository;
  private final AccommodationRepository accommodationRepository;
  private final UserRepository userRepository;

  public void createWishList(WishlistCreateRequest wishlistCreateRequest) {
    Accommodation accommodation =
        accommodationRepository.findById(wishlistCreateRequest.accommodationId())
            .orElseThrow(NoSuchElementException::new);

    User user =
        userRepository.findById(wishlistCreateRequest.userId())
            .orElseThrow(NoSuchElementException::new);

    wishlistRepository.save(Wishlist.createWishlist(user, accommodation));
  }

  /**
   * 리스트
   */
  public List<WishlistListResponse> wishlistList(String userId){
    return wishlistRepository.findWishlistByUserId(userId);
  }

  /**
   * 삭제
   */
  @Transactional
  public void deleteWishlist(Long wishlistId) {
    wishlistRepository.deleteById(wishlistId);
  }
}

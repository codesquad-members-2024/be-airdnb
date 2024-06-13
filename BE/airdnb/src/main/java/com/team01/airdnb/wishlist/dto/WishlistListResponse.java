package com.team01.airdnb.wishlist.dto;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.user.User;
import com.team01.airdnb.wishlist.Wishlist;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WishlistListResponse {
  private Long id;
  private WishlistAccommodation wishlistAccommodation;

  public WishlistListResponse(Wishlist wishlist) {
    this.id = wishlist.getId();
    this.wishlistAccommodation = new WishlistAccommodation(wishlist.getAccommodation());
  }

  @Getter
  static class WishlistAccommodation{
    Long id;
    String  title;
    public WishlistAccommodation(Accommodation accommodation) {
      this.id = accommodation.getId();
      this.title = accommodation.getTitle();
    }
  }
}

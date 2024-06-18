package com.team01.airdnb.wishlist.dto;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.amenity.AmenityStatus;
import com.team01.airdnb.wishlist.Wishlist;
import lombok.Getter;

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
    WishlistAmenity wishlistAmenity;


    public WishlistAccommodation(Accommodation accommodation) {
      this.id = accommodation.getId();
      this.title = accommodation.getTitle();
      if(accommodation.getAmenity() == null){
        this.wishlistAmenity = null;
      }
      if (accommodation.getAmenity() != null){
        this.wishlistAmenity = new WishlistAmenity(accommodation.getAmenity());
      }
    }
  }

  @Getter
  static class WishlistAmenity {
    Integer beds;
    Integer bathrooms;
    AmenityStatus kitchen;
    AmenityStatus dedicated_workspace;
    AmenityStatus tv;
    AmenityStatus washing_machine;
    AmenityStatus air_conditioning;
    AmenityStatus wireless_internet;
    AmenityStatus free_parking;
    AmenityStatus paid_parking;
    public WishlistAmenity(Amenity amenity){
      this.beds = amenity.getBeds();
      this.bathrooms = amenity.getBathrooms();
      this.kitchen = amenity.getKitchen();
      this.dedicated_workspace = amenity.getDedicatedWorkspace();
      this.tv = amenity.getTv();
      this.washing_machine = amenity.getWashingMachine();
      this.air_conditioning = amenity.getAirConditioner();
      this.wireless_internet = amenity.getWirelessInternet();
      this.free_parking = amenity.getFreeParking();
      this.paid_parking = amenity.getPaidParking();
    }
  }

}

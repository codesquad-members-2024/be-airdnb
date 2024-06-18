package com.team01.airdnb.accommadation.dto;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.amenity.Amenity;
import com.team01.airdnb.amenity.AmenityStatus;
import com.team01.airdnb.image.Image;
import com.team01.airdnb.user.User;
import java.util.List;
import java.util.stream.Collectors;

public record AccommodationRegisterRequest(
    String title,
    String content,
    Long price,
    Integer discount,
    Integer maxAdults,
    Integer maxChildren,
    Integer maxInfants,
    Integer maxPets,
    String address,
    List<String> images,
    AmenityStatus tv,
    AmenityStatus kitchen,
    AmenityStatus washing_machine,
    AmenityStatus free_parking,
    AmenityStatus paid_parking,
    AmenityStatus air_conditioning,
    AmenityStatus dedicated_workspace,
    AmenityStatus wireless_internet,
    Integer beds,
    Integer bathrooms,
    String userId


) {
  public AccommodationRegisterRequest {
    if (discount == null) discount = 0;
    if (maxAdults == null) maxAdults = 1;
    if (maxChildren == null) maxChildren = 0;
    if (maxInfants == null) maxInfants = 0;
    if (maxPets == null) maxPets = 0;
    if (tv == null) tv = AmenityStatus.NOT_AVAILABLE;
    if (kitchen == null) kitchen = AmenityStatus.NOT_AVAILABLE;
    if (washing_machine == null) washing_machine = AmenityStatus.NOT_AVAILABLE;
    if (free_parking == null) free_parking = AmenityStatus.NOT_AVAILABLE;
    if (paid_parking == null) paid_parking = AmenityStatus.NOT_AVAILABLE;
    if (air_conditioning == null) air_conditioning = AmenityStatus.NOT_AVAILABLE;
    if (dedicated_workspace == null) dedicated_workspace = AmenityStatus.NOT_AVAILABLE;
    if (wireless_internet == null) wireless_internet = AmenityStatus.NOT_AVAILABLE;
    if (beds == null) beds = 1;
    if (bathrooms == null) bathrooms = 0;
  }

  public Accommodation toAccommodationEntity(User user) {
    return Accommodation.builder()
        .title(this.title)
        .content(this.content)
        .price(this.price)
        .discountRate(this.discount)
        .maxAdults(this.maxAdults)
        .maxChildren(this.maxChildren)
        .maxInfants(this.maxInfants)
        .maxPets(this.maxPets)
        .address(this.address)
        .user(user)
        .build();
  }

  public List<Image> toImageEntity() {
    return this.images.stream()
        .map(imagePath -> Image.builder()
            .imagePath(imagePath)
            .build())
        .collect(Collectors.toList());
  }

  public Amenity toAmenityEntity() {
    return Amenity.builder()
        .tv(this.tv)
        .kitchen(this.kitchen)
        .washingMachine(this.washing_machine)
        .freeParking(this.free_parking)
        .paidParking(this.paid_parking)
        .airConditioner(this.air_conditioning)
        .dedicatedWorkspace(this.dedicated_workspace)
        .wirelessInternet(this.wireless_internet)
        .beds(this.beds)
        .bathrooms(this.bathrooms)
        .build();
  }
}

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
    String user_id
) {

  public Accommodation toAccommodationEntity(User user) {
    return Accommodation.builder()
        .title(title)
        .content(content)
        .price(price)
        .discount(discount)
        .maxAdults(maxAdults)
        .maxChildren(maxChildren)
        .maxInfants(maxInfants)
        .maxPets(maxPets)
        .address(address)
        .user(user)
        .build();
  }

  public List<Image> toImageEntity() {
    return images.stream()
        .map(imagePath -> Image.builder()
            .imagePath(imagePath)
            .build())
        .collect(Collectors.toList());
  }

  public Amenity toAmenityEntity() {
    return Amenity.builder()
        .tv(tv)
        .kitchen(kitchen)
        .washingMachine(washing_machine)
        .freeParking(free_parking)
        .paidParking(paid_parking)
        .airConditioner(air_conditioning)
        .dedicatedWorkspace(dedicated_workspace)
        .wirelessInternet(wireless_internet)
        .beds(beds)
        .bathrooms(bathrooms)
        .build();
  }
}

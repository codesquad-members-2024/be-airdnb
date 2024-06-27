package team03.airdnb.accommodation.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.Address;
import team03.airdnb.kakaoMap.dto.CoordinatesDto;
import team03.airdnb.user.User;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationSaveDto {

    private static final String SPACE = " ";

    private String name;
    private String profileImg;
    private Address address;
    private Long price;
    private int maxHeadCount;
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private Long hostId;
    private List<Long> amenityIds;

    public Accommodation toEntity(User host, String profileImg, CoordinatesDto coordinatesDto){
        return Accommodation.builder()
                .name(this.name)
                .profileImg(profileImg)
                .address(this.address)
                .price(this.price)
                .maxHeadCount(this.maxHeadCount)
                .bedCount(this.bedCount)
                .bedroomCount(this.bedroomCount)
                .bathroomCount(this.bathroomCount)
                .longitude(coordinatesDto.getLongitude())
                .latitude(coordinatesDto.getLatitude())
                .host(host)
                .build();
    }

    public String getFullAddress(){
        return address.getFirstAddress() + SPACE + address.getSecondAddress() + SPACE + address.getThirdAddress();
    }
}
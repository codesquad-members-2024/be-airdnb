package team03.airdnb.accommodation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.Address;
import team03.airdnb.user.User;

import java.util.List;

@Getter
@AllArgsConstructor
public class AccommodationSaveDto {

    private String name;
    private String profileImg;
    private Address address;
    private Long price;
    private int maxHeadCount;
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private double longitude; // 경도
    private double latitude; // 위도
    private Long hostId;
    private List<Long> amenityIds;

    public Accommodation toEntity(User host){
        return Accommodation.builder()
                .name(this.name)
                .profileImg(this.profileImg)
                .address(this.address)
                .price(this.price)
                .maxHeadCount(this.maxHeadCount)
                .bedCount(this.bedCount)
                .bedroomCount(this.bedroomCount)
                .bathroomCount(this.bathroomCount)
                .longitude(this.longitude)
                .latitude(this.latitude)
                .host(host)
                .build();
    }
}
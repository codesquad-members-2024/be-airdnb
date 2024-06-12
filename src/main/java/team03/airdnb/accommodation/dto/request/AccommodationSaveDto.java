package team03.airdnb.accommodation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.Address;
import team03.airdnb.user.User;

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
    private Long hostId;

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
                .host(host)
                .build();
    }
}

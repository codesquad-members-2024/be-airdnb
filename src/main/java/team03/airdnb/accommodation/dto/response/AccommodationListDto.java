package team03.airdnb.accommodation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.AccommodationAmenity.AccommodationAmenity;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.Address;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AccommodationListDto {

    private Long id;
    private String name;
    private String profileImg;
    private Address address;
    private Long price;
    private int headcount;
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private List<String> amenities;
    private double averageGrade;
    private int reviewCount;

    public static AccommodationListDto of(Accommodation accommodation, List<AccommodationAmenity> accommodationAmenities) {
        return new AccommodationListDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getProfileImg(),
                accommodation.getAddress(),
                accommodation.getPrice(),
                accommodation.getHeadCount(),
                accommodation.getBedCount(),
                accommodation.getBedroomCount(),
                accommodation.getBathroomCount(),
                amenitiesToStringList(accommodationAmenities),
                accommodation.getAverageGrade(),
                accommodation.getReviews().size()
        );
    }

    // 숙소의 amenity를 모두 string(amenity name) 리스트로 변환하기
    private static List<String> amenitiesToStringList(List<AccommodationAmenity> accommodationAmenities) {
        return accommodationAmenities.stream()
                .map(accommodationAmenity -> accommodationAmenity.getAmenity().getName())
                .collect(Collectors.toList());
    }
}

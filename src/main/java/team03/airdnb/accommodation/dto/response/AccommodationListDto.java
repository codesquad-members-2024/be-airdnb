package team03.airdnb.accommodation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.Address;
import team03.airdnb.accommodationAmenity.AccommodationAmenity;

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
    private int maxHeadCount;
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private double longitude; // 경도
    private double latitude; // 위도
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
                accommodation.getMaxHeadCount(),
                accommodation.getBedCount(),
                accommodation.getBedroomCount(),
                accommodation.getBathroomCount(),
                accommodation.getLongitude(),
                accommodation.getLatitude(),
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

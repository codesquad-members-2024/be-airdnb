package team03.airdnb.accommodation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.AccommodationAmenity.AccommodationAmenity;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.Address;
import team03.airdnb.review.Review;
import team03.airdnb.review.dto.response.ReviewShowDto;
import team03.airdnb.user.dto.response.UserShowDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class AccommodationShowDto {

    private Long id;
    private String name;
    private String profileImg;
    private Address address;
    private Long price;
    private Long fee;
    private int headcount;
    private int bedCount;
    private int bedroomCount;
    private int bathroomCount;
    private List<String> amenities;
    private double averageGrade;
    private int reviewCount;
    private List<ReviewShowDto> reviews;
    private UserShowDto host;

    public static AccommodationShowDto of(Accommodation accommodation, Long fee, List<AccommodationAmenity> accommodationAmenities) {
        return new AccommodationShowDto(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getProfileImg(),
                accommodation.getAddress(),
                accommodation.getPrice(),
                fee,
                accommodation.getHeadCount(),
                accommodation.getBedCount(),
                accommodation.getBedroomCount(),
                accommodation.getBathroomCount(),
                amenitiesToStringList(accommodationAmenities),
                accommodation.getAverageGrade(),
                accommodation.getReviews().size(),
                reviewsToDto(accommodation.getReviews()),
                UserShowDto.of(accommodation.getHost())
        );
    }

    // List<reviews>를 List<ReviewShowDto>로 변환하기
    private static List<ReviewShowDto> reviewsToDto(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewShowDto::of)
                .toList();
    }

    // 숙소의 amenity를 모두 string(amenity name) 리스트로 변환하기
    private static List<String> amenitiesToStringList(List<AccommodationAmenity> accommodationAmenities) {
        return accommodationAmenities.stream()
                .map(accommodationAmenity -> accommodationAmenity.getAmenity().getName())
                .collect(Collectors.toList());
    }
}
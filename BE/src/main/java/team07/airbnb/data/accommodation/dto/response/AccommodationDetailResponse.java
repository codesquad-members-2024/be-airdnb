package team07.airbnb.data.accommodation.dto.response;

import team07.airbnb.data.accommodation.enums.AccommodationType;
import team07.airbnb.data.user.dto.SimpleUserResponse;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.Pictures;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.entity.embed.AccommodationLocation;
import team07.airbnb.entity.embed.RoomInformation;

import java.util.List;

public record AccommodationDetailResponse(
        Long id,
        String name,
        String description,
        List<String> pictures,
        double rating,
        int numOrReviews,

        SimpleUserResponse host,
        AccommodationType type,
        RoomInformation roomInformation,
        AccommodationLocation location,
        List<ReviewEntity> reviews
) {

    public static AccommodationDetailResponse of(AccommodationEntity accommodation) {
        List<ReviewEntity> reviews = accommodation.reviews();

        return new AccommodationDetailResponse(
                accommodation.getId(),
                accommodation.getName(),
                accommodation.getDescription(),
                accommodation.getPictures().stream().map(Pictures::getUrl).toList(),
                accommodation.rating(),
                reviews.size(),
                SimpleUserResponse.of(accommodation.getHost()),
                accommodation.getType(),
                accommodation.getRoomInformation(),
                accommodation.getAddress(),
                reviews
        );
    }
}

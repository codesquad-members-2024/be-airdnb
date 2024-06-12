package team10.airdnb.accommodation_room_type.controller.request;

import jakarta.validation.constraints.NotBlank;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;

public record AccommodationRoomTypeCreateRequest(
        @NotBlank(message = "방 유형은 빈 값이 될 수 없습니다.")
        String name
) {
    public AccommodationRoomType toEntity() {
        return AccommodationRoomType.builder()
                .name(name)
                .build();
    }
}

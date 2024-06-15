package team10.airdnb.accommodation_type.controller.request;

import jakarta.validation.constraints.NotBlank;
import team10.airdnb.accommodation_room_type.entity.AccommodationRoomType;
import team10.airdnb.accommodation_type.entity.AccommodationType;

public record AccommodationTypeCreateRequest(
        @NotBlank(message = "방 유형은 빈 값이 될 수 없습니다.")
        String name
) {
        public AccommodationType toEntity() {
                return AccommodationType.builder()
                        .name(name)
                        .build();
        }
}

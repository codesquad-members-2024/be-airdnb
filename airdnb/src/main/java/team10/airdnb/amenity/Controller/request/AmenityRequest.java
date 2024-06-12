package team10.airdnb.amenity.Controller.request;

import jakarta.validation.constraints.NotBlank;
import team10.airdnb.amenity.entity.Amenity;

public record AmenityRequest (
        @NotBlank(message = "빈 값이 될 수 없습니다.")
        String name
){
    public Amenity toEntity() {
        return Amenity.builder()
                .name(name)
                .build();
    }
}

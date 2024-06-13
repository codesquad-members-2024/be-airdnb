package team07.airbnb.data.user.dto;

import team07.airbnb.entity.UserEntity;

public record SimpleUserResponse (
        Long id,
        String name,
        String profileImage
){
    public static SimpleUserResponse of(UserEntity user){
        return new SimpleUserResponse(
                user.getId(),
                user.getName(),
                user.getPicture()
        );
    }
}

package team07.airbnb.domain.user.dto;

import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.enums.Role;

public record TokenUserInfo(Long id, String name, String profileImg, Role role) {

    public static TokenUserInfo of(UserEntity userEntity) {
        return new TokenUserInfo(userEntity.getId(), userEntity.getName(), userEntity.getPicture(), userEntity.getRole());
    }

    public String stringRole() {
        return this.role.getKey();
    }
}

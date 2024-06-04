package team07.airbnb.domain.user.dto;

import lombok.Getter;
import team07.airbnb.domain.user.entity.UserEntity;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {

    private String name;
    private String email;
    private String picture;
    private String registrationId;

    public SessionUser(UserEntity user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.registrationId = user.getRegistrationId();
    }
}
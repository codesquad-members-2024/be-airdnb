package team07.airbnb.util;

import org.springframework.security.core.context.SecurityContextHolder;
import team07.airbnb.domain.user.entity.UserEntity;

public class UserHelper {

    public static UserEntity getCurrentUser(){
        return (UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}

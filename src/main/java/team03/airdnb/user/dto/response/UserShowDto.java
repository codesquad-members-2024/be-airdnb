package team03.airdnb.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.user.User;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UserShowDto {

    private Long id;
    private String name;
    private String profileImg;
    private String type;
    private LocalDateTime createdAt;

    public static UserShowDto of(User user){
        return new UserShowDto(
                user.getId(),
                user.getName(),
                user.getProfileImg(),
                user.getType(),
                user.getCreatedAt()
        );
    }
}

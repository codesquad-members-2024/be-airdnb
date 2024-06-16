package team03.airdnb.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import team03.airdnb.user.User;

@Getter
@Setter
@AllArgsConstructor
public class UserSaveDto {

    private String name;
    private String password;
    private String profileImg;

    public User toEntity(){
        return User.builder()
                .name(this.name)
                .password(this.password)
                .profileImg(this.profileImg)
                .build();
    }
}

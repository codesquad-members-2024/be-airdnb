package team03.airdnb.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.user.User;

@Getter
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

    public UserSaveDto withProvidedPassword(String password) {
        return new UserSaveDto(this.name, password, this.profileImg);
    }
}

package team03.airdnb.authentication.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginDto {

    private String name;
    @Setter
    private String password;
}

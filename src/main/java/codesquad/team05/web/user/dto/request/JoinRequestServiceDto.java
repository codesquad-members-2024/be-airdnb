package codesquad.team05.web.user.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class JoinRequestServiceDto {
    private String loginId;
    private String name;
    private String password;
    private String address;
    private LocalDate birthdate;


}

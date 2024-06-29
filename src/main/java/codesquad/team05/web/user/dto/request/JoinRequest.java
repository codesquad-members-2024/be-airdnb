package codesquad.team05.web.user.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
public class JoinRequest {


    private String loginId;
    private String name;
    private String password;
    private String address;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate birthdate;



    public JoinRequestServiceDto  toServiceDto() {
        JoinRequestServiceDto joinRequestServiceDto = new JoinRequestServiceDto();
        joinRequestServiceDto.setLoginId(loginId);
        joinRequestServiceDto.setName(name);
        joinRequestServiceDto.setPassword(password);
        joinRequestServiceDto.setAddress(address);
        joinRequestServiceDto.setBirthdate(birthdate);

        return joinRequestServiceDto;
    }
}

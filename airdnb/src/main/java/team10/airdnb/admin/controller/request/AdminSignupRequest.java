package team10.airdnb.admin.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import team10.airdnb.admin.entity.Admin;

public record AdminSignupRequest(
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        @NotBlank(message = "이메일 주소는 비워둘 수 없습니다.")
        String adminId,

        @Size(max = 6, min = 6, message = "인증번호는 6자로 입력해주세요.")
        @NotBlank(message = "인증 번호는 비워둘 수 없습니다.")
        String authNumber,

        @NotBlank(message = "비밀번호는 비워둘 수 없습니다.")
        String password
) {
    public Admin toEntity() {
        return new Admin(adminId, password);
    }
}

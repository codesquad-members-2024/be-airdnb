package team10.airdnb.admin.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminEmailRequest(
        @Email(message = "유효한 이메일 주소를 입력해주세요.")
        @NotBlank(message = "이메일 주소는 비워둘 수 없습니다.")
        String adminId
) {}

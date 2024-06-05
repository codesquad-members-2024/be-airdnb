package team10.airdnb.admin.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminEmailCheckRequest {
    @Email
    @NotEmpty
    private final String adminId;

    @NotEmpty
    private final int authNum;
}

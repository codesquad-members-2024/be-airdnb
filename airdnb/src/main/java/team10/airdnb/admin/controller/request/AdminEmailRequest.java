package team10.airdnb.admin.controller.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
public class AdminEmailRequest {
    @Email
    @NotEmpty
    private String adminId;
}

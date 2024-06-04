package team10.airdnb.admin.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team10.airdnb.admin.entity.Admin;

@Getter
@AllArgsConstructor
public class AdminRegisterRequest {
    private final String adminId;
    private final String password;

    public Admin toEntity() {
        return new Admin(adminId, password);
    }
}

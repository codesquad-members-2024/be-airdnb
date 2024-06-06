package team10.airdnb.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team10.airdnb.admin.controller.request.AdminSignupRequest;
import team10.airdnb.admin.entity.Admin;
import team10.airdnb.admin.repository.AdminRepository;
import team10.airdnb.email.service.EmailService;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final EmailService emailService;

    private final AdminRepository adminRepository;

    public Admin registerAdmin(AdminSignupRequest request) {
        emailService.validateAuthCode(request.adminId(), request.authCode());

        Admin admin = request.toEntity();

        return adminRepository.save(admin);
    }
}

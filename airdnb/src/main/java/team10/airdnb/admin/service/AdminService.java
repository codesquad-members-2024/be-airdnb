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
        // 1) 회원가입 폼에 대한 검증
        emailService.checkAuthNum(request.adminId(), request.authNumber());

        // 2) Request Dto를 Entity로 변환
        Admin admin = request.toEntity();

        // 3) admin Repository에 저장
        return adminRepository.save(admin);
    }
}

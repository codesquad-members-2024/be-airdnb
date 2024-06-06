package team10.airdnb.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.admin.controller.request.AdminSignupRequest;
import team10.airdnb.admin.controller.request.AdminEmailRequest;
import team10.airdnb.admin.controller.response.AdminSignupResponse;
import team10.airdnb.admin.entity.Admin;
import team10.airdnb.admin.service.AdminService;
import team10.airdnb.email.service.EmailService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final EmailService emailService;
    private final AdminService adminService;

    @PostMapping
    public ResponseEntity<AdminSignupResponse> adminSignup(@RequestBody @Valid AdminSignupRequest request) {
        Admin admin = adminService.registerAdmin(request);

        log.debug("admin 회원가입 완료 : {}", admin.getAdminId());

        AdminSignupResponse response = new AdminSignupResponse(admin.getAdminId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestBody @Valid AdminEmailRequest request) {
        String authNumber = emailService.joinEmail(request);

        log.debug("발급된 AuthCode : {}", authNumber);

        return ResponseEntity.ok().build();
    }

}


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
    public ResponseEntity<?> adminSignup(@RequestBody @Valid AdminSignupRequest request) {
        Admin admin = adminService.registerAdmin(request);
        log.debug("admin : {}", admin.getAdminId());
        return ResponseEntity.ok(admin);
    }

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestBody @Valid AdminEmailRequest request) {
        log.debug("request {}", request.adminId());
        return ResponseEntity.ok(emailService.joinEmail(request.adminId()));
    }


}


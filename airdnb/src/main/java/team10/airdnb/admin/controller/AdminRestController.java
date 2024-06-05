package team10.airdnb.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.admin.controller.request.AdminEmailCheckRequest;
import team10.airdnb.admin.controller.request.AdminEmailRequest;
import team10.airdnb.email.service.EmailService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final EmailService emailService;

    @PostMapping("/send-mail")
    public ResponseEntity<?> sendMail(@RequestBody @Valid AdminEmailRequest request) {
        log.debug("request {}", request.adminId());
        return ResponseEntity.ok(emailService.joinEmail(request.adminId()));
    }

    @PostMapping("/auth-check")
    public ResponseEntity<?> mailAuthCheck(@RequestBody @Valid AdminEmailCheckRequest request) {
        emailService.mailAuthCheck(request.adminId(), request.authNumber());
        return ResponseEntity.ok("ok");
    }
}


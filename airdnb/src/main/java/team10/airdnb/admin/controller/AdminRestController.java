package team10.airdnb.admin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.admin.controller.request.AdminEmailRequest;
import team10.airdnb.email.service.EmailService;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final EmailService emailService;

    @PostMapping("/sendMail")
    public ResponseEntity<?> registerAdmin(@RequestBody @Valid AdminEmailRequest request) {
        log.debug("request {}", request.getAdminId());
        return ResponseEntity.ok(emailService.joinEmail(request.getAdminId()));
    }
}


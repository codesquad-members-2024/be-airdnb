package team10.airdnb.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team10.airdnb.admin.entity.Admin;
import team10.airdnb.admin.controller.request.AdminRegisterRequest;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @PostMapping("/sendMail")
    public ResponseEntity<?> registerAdmin(@RequestBody AdminRegisterRequest request) {
        Admin admin = request.toEntity();

        return ResponseEntity.ok("ok");
    }
}

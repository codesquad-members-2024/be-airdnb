package team10.airdnb.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/register")
    public String getAdminRegisterPage() {
        return "admin/main"; // "admin/main.html" 템플릿을 반환
    }

    @GetMapping("/login")
    public String getAdminLoginPage() {
        return "admin/login";
    }
}

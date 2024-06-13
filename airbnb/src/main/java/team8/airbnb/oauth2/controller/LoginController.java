package team8.airbnb.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


  @GetMapping("/success")
  public String successPage() {
    return "loginSuccess";
  }

  @GetMapping("/check")
  public String check() {
    return "checkpage";
  }

  @GetMapping("/total/login")
  public String totalLoginPage() {
    return "totallogin";
  }
}

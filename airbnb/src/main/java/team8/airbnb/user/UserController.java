package team8.airbnb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team8.airbnb.jwt.jwtToken.JwtUtil;
import team8.airbnb.jwt.loginDto.LoginRequest;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final JwtUtil jwtUtil;

  @PostMapping("/login")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
    User foundUser = userService.findByUsername(loginRequest.username());
    if (foundUser != null && foundUser.getPassword().equals(loginRequest.password())) {
      String token = jwtUtil.generateToken(foundUser.getUsername());
      return ResponseEntity.ok(token);
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패");
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/registration")
  public void save(@RequestBody User user) {
    userService.save(user);
  }
}

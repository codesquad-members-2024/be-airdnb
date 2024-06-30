package codesquad.team05.web.user;

import codesquad.team05.service.UserService;
import codesquad.team05.web.user.dto.request.JoinRequest;
import codesquad.team05.web.user.dto.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping
    public void join(@RequestBody JoinRequest joinRequest) {
        userService.save(joinRequest.toServiceDto());
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest loginRequest) {
        userService.login(loginRequest);
    }

    @GetMapping("/login/google")
    public String loginSuccess() {
        return "Hello! from the Google";
    }


}

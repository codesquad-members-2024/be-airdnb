package codesquad.team05.web.user;

import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    // Service 주입으로 변경 예정
    private final UserRepository userRepository;

    // Dto 사용으로 수정 예정
    @PostMapping("/users")
    public void join(@RequestBody User user) {
        userRepository.save(user);
    }

    @GetMapping("/users/{loginId}")
    public User userDetails(@PathVariable String loginId) {
        return userRepository.findById(loginId).get(); // Optional 처리 예정
    }
}

package team8.airbnb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping("/registration")
  public void save(@RequestBody User user) {
    userService.save(user);
  }
}
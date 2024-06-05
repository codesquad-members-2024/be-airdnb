package team8.airbnb.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void save(User user) {
    User savedUser = userRepository.save(user);
    log.debug("가입된 유저: {}", savedUser.getUsername());
  }
}
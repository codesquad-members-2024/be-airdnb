package team8.airbnb.user;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Long save(User user) {
    List<User> usersByEmail = userRepository.findByEmail(user.getEmail());
    if (!usersByEmail.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
    }

    User existingPhoneNumber = userRepository.findByPhoneNumber(user.getPhoneNumber());
    if (existingPhoneNumber != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 전화번호입니다.");
    }

    User existingUser = userRepository.findByUsername(user.getUsername());
    if (existingUser != null) {
      throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 회원의 이름입니다.");
    }

    User savedUser = userRepository.save(user);
    return savedUser.getId();
  }
}
package com.team01.airdnb.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

  @Autowired
  UserRepository userRepository;

  @Test
  @DisplayName("유저 생성 테스트")
  void createUsers() {
    User user = new User();
    user.setUsername("username");
    user.setEmail("email");
    user.setProfile("profile");
    user.setProvider("provider");
    user.setRole(Role.USER);
    User savedUser = userRepository.save(user);

    assertThat(savedUser).isNotNull();
    assertThat(savedUser.getId()).isNotNull();
    assertThat(savedUser.getUsername()).isEqualTo("username");
    assertThat(savedUser.getEmail()).isEqualTo("email");
    assertThat(savedUser.getProfile()).isEqualTo("profile");
    assertThat(savedUser.getProvider()).isEqualTo("provider");
    assertThat(savedUser.getRole()).isEqualTo(Role.USER);
  }

  @Test
  @DisplayName("유저 조회 테스트")
  void findUserById() {
    User user = new User();
    user.setUsername("username");
    user.setEmail("email");
    user.setProfile("profile");
    user.setProvider("provider");
    user.setRole(Role.USER);
    User savedUser = userRepository.save(user);

    Optional<User> foundUser = userRepository.findById(savedUser.getId());

    assertThat(foundUser).isPresent();
    assertThat(foundUser.get().getUsername()).isEqualTo("username");
    assertThat(foundUser.get().getEmail()).isEqualTo("email");
    assertThat(foundUser.get().getProfile()).isEqualTo("profile");
    assertThat(foundUser.get().getProvider()).isEqualTo("provider");
    assertThat(foundUser.get().getRole()).isEqualTo(Role.USER);
  }

  @Test
  @DisplayName("유저 업데이트 테스트")
  void updateUser() {
    User user = new User();
    user.setUsername("username");
    user.setEmail("email");
    user.setProfile("profile");
    user.setProvider("provider");
    user.setRole(Role.USER);
    User savedUser = userRepository.save(user);

    savedUser.setUsername("newUsername");
    savedUser.setEmail("newEmail");
    User updatedUser = userRepository.save(savedUser);

    assertThat(updatedUser.getUsername()).isEqualTo("newUsername");
    assertThat(updatedUser.getEmail()).isEqualTo("newEmail");
  }

  @Test
  @DisplayName("유저 삭제 테스트")
  void deleteUser() {
    User user = new User();
    user.setUsername("username");
    user.setEmail("email");
    user.setProfile("profile");
    user.setProvider("provider");
    user.setRole(Role.USER);
    User savedUser = userRepository.save(user);

    userRepository.delete(savedUser);
    Optional<User> deletedUser = userRepository.findById(savedUser.getId());

    assertThat(deletedUser).isNotPresent();
  }
}

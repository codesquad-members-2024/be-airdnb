package com.team01.airdnb.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
  @DisplayName("유저 10명 생성 테스트")
  void createUsers() {
    User user = new User();
    user.setId("user");
    user.setUsername("username");
    user.setPassword("password");
    user.setRole(Role.CLIENT);
    user.setAge(20);
    userRepository.save(user);

    assertThat(user.getId()).isEqualTo("user");
    assertThat(user.getUsername()).isEqualTo("username");
    assertThat(user.getPassword()).isEqualTo("password");
    assertThat(user.getRole()).isEqualTo(Role.CLIENT);
    assertThat(user.getAge()).isEqualTo(20 );
  }
}

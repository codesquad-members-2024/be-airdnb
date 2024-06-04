package com.team01.airdnb.user;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {
  @Autowired UserRepository userRepository;

  @Test
  @DisplayName("create users table test")
  @Rollback(value = false)
  void createUsersTable() {
    //given
    User user = new User();
    user.setId("testId");

    //when
    User savedUser = userRepository.save(user);

    //then
    assertThat(savedUser.getId()).isEqualTo(user.getId());
  }
}

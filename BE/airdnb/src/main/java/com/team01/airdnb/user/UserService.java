package com.team01.airdnb.user;

import com.team01.airdnb.comment.CommentRepository;
import com.team01.airdnb.user.dto.UserHostResponse;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class UserService {
  private final UserRepository userRepository;
  private final CommentRepository commentRepository;

  @Autowired
  public UserService(UserRepository userRepository, CommentRepository commentRepository) {
    this.userRepository = userRepository;
    this.commentRepository = commentRepository;
  }

  public User FindUserById(Long userId){
    return userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("해당하는 유저가 존재하지 않습니다"));
  }

  public UserHostResponse getHostResponse(User user) {
    return UserHostResponse.builder()
        .username(user.getUsername())
        .score(commentRepository.findAverageScoreByUser(user))
        .build();
  }

  public Optional<User> findByEmail(String email){
    return userRepository.findByEmail(email);
  }
}

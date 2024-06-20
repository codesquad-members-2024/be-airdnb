package com.team01.airdnb.user;

import com.team01.airdnb.comment.CommentRepository;
import com.team01.airdnb.user.dto.UserHostResponse;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  UserRepository userRepository;
  CommentRepository commentRepository;

  public UserService(UserRepository userRepository, CommentRepository commentRepository){
    this. userRepository = userRepository;
    this.commentRepository = commentRepository;
  }

  public User FindUserById(String userId){
    return userRepository.findById(userId)
        .orElseThrow(() -> new NoSuchElementException("해당하는 유저가 존재하지 않습니다"));
  }

  public UserHostResponse getHostResponse(User user){
    return UserHostResponse.builder()
        .username(user.username)
        .score(commentRepository.findAverageScoreByUser(user))
        .build();
  }
}

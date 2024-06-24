package com.team01.airdnb.user;

import com.team01.airdnb.comment.CommentRepository;
import com.team01.airdnb.user.dto.UserHostResponse;
import com.team01.airdnb.user.dto.UserShowResponse;
import java.util.ArrayList;
import java.util.List;
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
  public Optional<User> findBySocialId(String socialId){return userRepository.findBySocialId(socialId);}
  public User save(User user){return userRepository.save(user);}

  public List<UserShowResponse> findAll(){
    List<User> users = userRepository.findAll();
    List<UserShowResponse> userShowResponses = new ArrayList<>();

    for(User user : users){
      userShowResponses.add(UserShowResponse.builder()
          .id(user.getId())
          .username(user.getUsername())
          .email(user.getEmail())
          .profile(user.getProfile())
          .role(user.getRole())
          .socialType(user.getSocialType())
          .socialId(user.getSocialId())
          .build());
    }
    return userShowResponses;
  }
}

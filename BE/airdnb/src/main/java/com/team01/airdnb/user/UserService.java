package com.team01.airdnb.user;

import org.springframework.stereotype.Service;

@Service
public class UserService {
  UserRepository userRepository;

  public UserService(UserRepository userRepository){
    this. userRepository = userRepository;
  }

  public User FindUserById(String userId){
    return userRepository.findById(userId).orElseThrow();
  }
}

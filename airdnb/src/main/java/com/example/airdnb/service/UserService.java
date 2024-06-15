package com.example.airdnb.service;

import com.example.airdnb.domain.user.User;
import com.example.airdnb.dto.user.UserCreateRequest;
import com.example.airdnb.dto.user.UserResponse;
import com.example.airdnb.repository.UserRepository;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Long join(UserCreateRequest request) {
        User user = User.builder()
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .name(request.name())
            .role(request.role())
            .build();

        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    public UserResponse findById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(NoSuchElementException::new);
        return UserResponse.fromUser(user);
    }

}

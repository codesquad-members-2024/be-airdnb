package team07.airbnb.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity findAnyUser(){ // SecurityContextHolder 구현 전 임시 사용
        return userRepository.findById(1L).get();
    }
}

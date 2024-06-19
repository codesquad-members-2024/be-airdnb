package team07.airbnb.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.entity.ProductEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveChanged(UserEntity user) {
        userRepository.save(user);
    }

    public void addFavorite(Long userId, ProductEntity product) {
        userRepository.save(getById(userId).addFavorite(product));
    }

    public void removeFavorite(Long userId, ProductEntity product) {
        userRepository.save(getById(userId).removeFavorite(product));
    }

    @Transactional
    public void userGrantToHost(UserEntity user) {
        user.setRoleToHost();
        userRepository.save(user);
    }

    @Transactional
    public void userGrantToUser(UserEntity user) {
        user.setRoleToUser();
        userRepository.save(user);
    }

    private UserEntity getById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저 " + id));
    }
}

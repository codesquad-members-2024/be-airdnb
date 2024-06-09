package team07.airbnb.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.product.entity.ProductEntity;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    // JWT 토큰의 유저 정보로 부족할때 온전한 UserEntity 사용
    public UserEntity getCompleteUser(UserEntity user){
        return userRepository.findById(user.getId()).get();
    }

    public void saveChanged(UserEntity user){
        userRepository.save(user);
    }

    public void userGrantToHost(UserEntity user) {

    }

    public void addFavorite(UserEntity user, ProductEntity product){
        userRepository.save(getCompleteUser(user).addFavorite(product));
    }

    public void removeFavorite(UserEntity user, ProductEntity product) {
        userRepository.save(getCompleteUser(user).removeFavorite(product));
    }
}

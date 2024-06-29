package team03.airdnb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.exception.duplicate.DuplicateNameException;
import team03.airdnb.exception.notFound.UserNotFoundException;
import team03.airdnb.favorite.Favorite;
import team03.airdnb.reservation.dto.response.ReservationShowDto;
import team03.airdnb.review.Review;
import team03.airdnb.review.dto.response.ReviewShowDto;
import team03.airdnb.user.dto.request.UserSaveDto;
import team03.airdnb.user.dto.response.UserShowDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserSaveDto userSaveDto) {
        if (userRepository.existsByName(userSaveDto.getName())) { // name 중복 확인
            throw new DuplicateNameException();
        }
        String encodedPassword = passwordEncoder.encode(userSaveDto.getPassword());
        return userRepository.save(userSaveDto.withProvidedPassword(encodedPassword).toEntity());
    }

    public UserShowDto showLoginUser(String name) {
        User loginUser = userRepository.findByName(name).orElseThrow(UserNotFoundException::new);
        return UserShowDto.of(loginUser);
    }

    public List<ReservationShowDto> showReservations(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        return user.getReservations().stream()
                .map(ReservationShowDto::of)
                .collect(Collectors.toList());
    }

    public List<AccommodationListDto> showFavorites(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Favorite> favorites = user.getFavorites();

        return favorites.stream()
                .map(favorite -> {
                    Accommodation accommodation = favorite.getAccommodation();
                    return AccommodationListDto.of(accommodation, accommodation.getAccommodationAmenities());
                })
                .collect(Collectors.toList());
    }

    public List<ReviewShowDto> showReviews(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        List<Review> reviews = user.getReviews();

        return reviews.stream().map(ReviewShowDto::of).collect(Collectors.toList());
    }
}

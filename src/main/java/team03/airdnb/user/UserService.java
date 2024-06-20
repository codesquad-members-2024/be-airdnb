package team03.airdnb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
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
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(UserSaveDto userSaveDto) {
        String encodedPassword = passwordEncoder.encode(userSaveDto.getPassword());
        return userRepository.save(userSaveDto.withEncodedPassword(encodedPassword).toEntity());
    }

    public UserShowDto showLoginUser(String name) {
        User loginUser = userRepository.findByName(name).get();
        return UserShowDto.of(loginUser);
    }

    public List<ReservationShowDto> showReservations(Long userId) {
        User user = userRepository.findById(userId).get();

        return user.getReservations().stream()
                .map(ReservationShowDto::of)
                .collect(Collectors.toList());
    }

    public List<AccommodationListDto> showFavorites(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Favorite> favorites = user.getFavorites();

        return favorites.stream()
                .map(favorite -> {
                    Accommodation accommodation = favorite.getAccommodation();
                    return AccommodationListDto.of(accommodation, accommodation.getAccommodationAmenities());
                })
                .collect(Collectors.toList());
    }

    public List<ReviewShowDto> showReviews(Long userId) {
        User user = userRepository.findById(userId).get();
        List<Review> reviews = user.getReviews();

        return reviews.stream().map(ReviewShowDto::of).collect(Collectors.toList());
    }
}

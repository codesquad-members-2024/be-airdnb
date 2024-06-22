package team03.airdnb.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.exception.AccommodationNotFoundException;
import team03.airdnb.exception.ErrorCode;
import team03.airdnb.exception.UserNotFoundException;
import team03.airdnb.favorite.dto.FavoriteSaveDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public Long createFavorite(@RequestBody FavoriteSaveDto favoriteSaveDto) {

        User user = userRepository.findById(favoriteSaveDto.getUserId()).orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        Accommodation accommodation = accommodationRepository.findById(favoriteSaveDto.getAccommodationId()).orElseThrow(() -> new AccommodationNotFoundException(ErrorCode.ACCOMMODATION_NOT_FOUND));
        Favorite savedFavorite = favoriteRepository.save(favoriteSaveDto.toEntity(user, accommodation));
        return savedFavorite.getId();
    }

    public void deleteFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

}

package team03.airdnb.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.exception.notFound.AccommodationNotFoundException;
import team03.airdnb.exception.notFound.UserNotFoundException;
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

        User user = userRepository.findById(favoriteSaveDto.getUserId()).orElseThrow(UserNotFoundException::new);
        Accommodation accommodation = accommodationRepository.findById(favoriteSaveDto.getAccommodationId()).orElseThrow(AccommodationNotFoundException::new);
        Favorite savedFavorite = favoriteRepository.save(favoriteSaveDto.toEntity(user, accommodation));
        return savedFavorite.getId();
    }

    public void deleteFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

}

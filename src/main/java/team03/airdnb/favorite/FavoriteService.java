package team03.airdnb.favorite;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.accommodation.AccommodationRepository;
import team03.airdnb.favorite.dto.FavoriteSaveDto;
import team03.airdnb.user.User;
import team03.airdnb.user.UserRepository;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final AccommodationRepository accommodationRepository;

    public Favorite createFavorite(@RequestBody FavoriteSaveDto favoriteSaveDto) {

        User user = userRepository.findById(favoriteSaveDto.getUserId()).get();
        Accommodation accommodation = accommodationRepository.findById(favoriteSaveDto.getAccommodationId()).get();
        return favoriteRepository.save(favoriteSaveDto.toEntity(user, accommodation));
    }

    public void deleteFavorite(Long favoriteId) {
        favoriteRepository.deleteById(favoriteId);
    }

}

package team03.airdnb.favorite.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import team03.airdnb.accommodation.Accommodation;
import team03.airdnb.favorite.Favorite;
import team03.airdnb.user.User;

@Getter
@AllArgsConstructor
public class FavoriteSaveDto {

    private Long id;
    private Long userId;
    private Long accommodationId;

    public Favorite toEntity(User user, Accommodation accommodation) {
        return Favorite.builder()
                .id(this.id)
                .user(user)
                .accommodation(accommodation)
                .build();
    }
}

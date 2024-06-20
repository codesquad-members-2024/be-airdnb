package team03.airdnb.favorite;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.favorite.dto.FavoriteSaveDto;

@RequestMapping("/favorites")
@RestController
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Void> createFavorite(@RequestBody FavoriteSaveDto favoriteSaveDto, UriComponentsBuilder uriComponentsBuilder) {
        Long favoriteId = favoriteService.createFavorite(favoriteSaveDto);
        URI location = uriComponentsBuilder.path("/favorites/{id}")
                .buildAndExpand(favoriteId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @DeleteMapping("/{favoriteId}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable Long favoriteId) {
       favoriteService.deleteFavorite(favoriteId);

       return ResponseEntity
               .noContent()
               .build();
    }
}

package team03.airdnb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.reservation.dto.response.ReservationShowDto;
import team03.airdnb.review.dto.response.ReviewShowDto;
import team03.airdnb.user.dto.request.UserSaveDto;
import team03.airdnb.user.dto.response.UserShowDto;

import java.net.URI;
import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody UserSaveDto userSaveDto, UriComponentsBuilder uriComponentsBuilder) {
        User createdUser = userService.createUser(userSaveDto);
        URI location = uriComponentsBuilder.path("/users/{id}")
                .buildAndExpand(createdUser.getId())
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @GetMapping
    public ResponseEntity<UserShowDto> showLoginUser(@AuthenticationPrincipal UserDetails userDetails) {
        UserShowDto userShowDto = userService.showLoginUser(userDetails.getUsername());
        return ResponseEntity.ok(userShowDto);
    }

    @GetMapping("/{userId}/reservations")
    public ResponseEntity<List<ReservationShowDto>> showReservations(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.showReservations(userId));
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<AccommodationListDto>> showFavorites(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.showFavorites(userId));
    }

    @GetMapping("/{userId}/reviews")
    public ResponseEntity<List<ReviewShowDto>> showReviews(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.showReviews(userId));
    }
}

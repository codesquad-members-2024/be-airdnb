package team03.airdnb.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team03.airdnb.accommodation.dto.response.AccommodationListDto;
import team03.airdnb.reservation.dto.response.ReservationShowDto;
import team03.airdnb.review.dto.response.ReviewShowDto;

import java.util.List;

@RequestMapping("/users")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

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

package team07.airbnb.domain.review;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.domain.accommodation.AccommodationService;
import team07.airbnb.domain.auth.aop.Authenticated;
import team07.airbnb.domain.booking.BookingService;
import team07.airbnb.domain.review.dto.ReviewPostRequest;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.enums.Role;
import team07.airbnb.domain.user.service.UserService;

import java.util.List;

@Tag(name = "예약")
@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final AccommodationService accommodationService;
    private final BookingService bookingService;
    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/{accommodationId}")
    public List<ReviewEntity> getReviews(@PathVariable long accommodationId) {
        return accommodationService.findById(accommodationId).reviews();
    }

    @Authenticated(Role.USER)
    @PostMapping("/{bookingId}")
    void postReview(@PathVariable long bookingId, @RequestBody ReviewPostRequest request, UserEntity user) {
        bookingService.addReview(bookingId, user.getId(), new ReviewEntity(bookingId, request.content(), request.rating()));
    }

    @Authenticated(Role.HOST)
    @PostMapping("/{reviewId}/reply")
    void replyToReview(@PathVariable long reviewId, @RequestBody String content ,UserEntity user){
        reviewService.addReplyTo(reviewId, content, userService.getCompleteUser(user));
    }
}

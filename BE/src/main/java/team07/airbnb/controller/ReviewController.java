package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.review.dto.request.ReviewPostRequest;
import team07.airbnb.data.user.dto.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.ReviewEntity;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.booking.BookingService;
import team07.airbnb.service.review.ReviewService;
import team07.airbnb.service.user.UserService;

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
    public List<ReviewEntity> getReviews(@PathVariable Long accommodationId) {
        return accommodationService.findById(accommodationId).reviews();
    }

    @Authenticated(Role.USER)
    @PostMapping("/{bookingId}")
    void postReview(@PathVariable Long bookingId, @RequestBody ReviewPostRequest request, TokenUserInfo user) {
        bookingService.addReview(bookingId, user.id(), new ReviewEntity(bookingId, request.content(), request.rating()));
    }

    @Authenticated(Role.HOST)
    @PostMapping("/{reviewId}/reply")
    void replyToReview(@PathVariable Long reviewId, @RequestBody String content, TokenUserInfo user) {
        reviewService.addReplyTo(reviewId, content, userService.getCompleteUser(user));
    }
}

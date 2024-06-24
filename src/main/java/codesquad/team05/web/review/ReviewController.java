package codesquad.team05.web.review;

import codesquad.team05.service.ReviewService;
import codesquad.team05.web.review.dto.request.ReviewSave;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{userId}/{accommodationId}")
    public ResponseEntity<Long> writeReview(
            @PathVariable Long userId,
            @PathVariable Long accommodationId,
            @RequestBody ReviewSave reviewSave,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Long reviewId = reviewService.writeReview(userId, accommodationId, reviewSave);
        URI location = uriComponentsBuilder.path("/Reviews/{id}")
                .buildAndExpand(reviewId)
                .toUri();
        return ResponseEntity
                .created(location)
                .body(reviewId);
    }
}

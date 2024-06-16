package team03.airdnb.review;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import team03.airdnb.review.dto.request.ReviewSaveDto;
import team03.airdnb.review.dto.request.ReviewUpdateDto;

import java.net.URI;

@RequestMapping("/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Void> createReview(@RequestBody ReviewSaveDto reviewSaveDto, UriComponentsBuilder uriComponentsBuilder) {
        Long reviewId = reviewService.createReview(reviewSaveDto);
        URI location = uriComponentsBuilder.path("/reviews/{id}")
                .buildAndExpand(reviewId)
                .toUri();

        return ResponseEntity
                .created(location)
                .build();
    }

    @PutMapping
    public ResponseEntity<Void> updateReview(@RequestBody ReviewUpdateDto reviewUpdateDto) {
        reviewService.updateReview(reviewUpdateDto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);

        return ResponseEntity
                .noContent()
                .build();
    }
}

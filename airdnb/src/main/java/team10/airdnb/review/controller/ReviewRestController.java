package team10.airdnb.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.review.controller.request.ReviewCreateRequest;
import team10.airdnb.review.controller.request.ReviewUpdateRequest;
import team10.airdnb.review.controller.response.ReviewSummaryResponse;
import team10.airdnb.review.service.ReviewService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ReviewRestController {

    private final ReviewService reviewService;

    @PostMapping("/api/review")
    public ResponseEntity<?> createReview(@RequestBody @Valid ReviewCreateRequest request) {

        ReviewSummaryResponse response = reviewService.createReview(request);

        log.info("리뷰 생성 완료 : # {} : 리뷰 작성자  : {}, 숙소 이름 : {}, 댓글 내용 : {}, 별점 : {}",
                response.reviewId(),
                response.memberInformation().memberName(),
                response.accommodationInformation().accommodationName(),
                response.comment(),
                response.rate());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/api/review/{reviewId}")
    public ResponseEntity<?> updateReview(@RequestBody ReviewUpdateRequest request,
                                          @PathVariable long reviewId) {
        ReviewSummaryResponse response = reviewService.updateReview(reviewId, request);

        log.info("리뷰 수정 완료 : # {} : 리뷰 작성자  : {}, 숙소 이름 : {}, 수정된 댓글 내용 : {}, 수정된 별점 : {}",
                response.reviewId(),
                response.memberInformation().memberName(),
                response.accommodationInformation().accommodationName(),
                response.comment(),
                response.rate());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/review/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable long reviewId) {
        ReviewSummaryResponse response = reviewService.deleteReview(reviewId);

        log.info("리뷰 삭제 완료 : # {} : 리뷰 작성자  : {}, 숙소 이름 : {}, 수정된 댓글 내용 : {}, 수정된 별점 : {}",
                response.reviewId(),
                response.memberInformation().memberName(),
                response.accommodationInformation().accommodationName(),
                response.comment(),
                response.rate());

        return ResponseEntity.ok(response);
    }
}

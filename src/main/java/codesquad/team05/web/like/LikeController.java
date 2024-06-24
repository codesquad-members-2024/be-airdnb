package codesquad.team05.web.like;

import codesquad.team05.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{userId}/{accommodationId}")
    public ResponseEntity<Long> like(
            @PathVariable Long userId,
            @PathVariable Long accommodationId
    ) {
        Long likeId = likeService.like(userId, accommodationId);
        return ResponseEntity
                .ok()
                .body(likeId);
    }
}

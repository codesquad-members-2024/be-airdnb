package codesquad.team05.web.like;

import codesquad.team05.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/likes")
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{userId}/{accommodationId}")
    public ResponseEntity<Long> like(
            @PathVariable Long userId,
            @PathVariable Long accommodationId,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        Long likeId = likeService.like(userId, accommodationId);
        URI location = uriComponentsBuilder.path("/likes/{id}")
                .buildAndExpand(likeId)
                .toUri();
        return ResponseEntity
                .created(location)
                .build();
    }
}

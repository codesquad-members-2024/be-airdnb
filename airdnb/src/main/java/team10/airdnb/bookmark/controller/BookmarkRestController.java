package team10.airdnb.bookmark.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import team10.airdnb.bookmark.controller.request.BookmarkRequest;
import team10.airdnb.bookmark.controller.response.BookmarkSummaryResponse;
import team10.airdnb.bookmark.service.BookmarkService;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BookmarkRestController {

    private final BookmarkService bookmarkService;

    @PostMapping("/bookmark")
    public ResponseEntity<?> createBookmark(@RequestBody @Valid BookmarkRequest request) {
        BookmarkSummaryResponse response = bookmarkService.createBookmark(request);

        log.info("북마크 생성 완료 : # {} : 숙소 이름 : {}, 멤버 이름 : {}",
                response.bookmarkId(),
                response.accommodationInformation().accommodationName(),
                response.memberInformation().memberName()
        );

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/bookmark")
    public ResponseEntity<?> deleteBookmark(@RequestBody @Valid BookmarkRequest request) {
        BookmarkSummaryResponse response = bookmarkService.deleteBookmark(request);

        log.info("북마크 삭제 완료 : # {} : 숙소 이름 : {}, 멤버 이름 : {}",
                response.bookmarkId(),
                response.accommodationInformation().accommodationName(),
                response.memberInformation().memberName()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookmark/exists")
    public ResponseEntity<Boolean> isBookmarked(@RequestBody @Valid BookmarkRequest request) {
        boolean isBookmarked = bookmarkService.checkBookmarked(request);

        return ResponseEntity.ok(isBookmarked);
    }
}

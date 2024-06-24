package codesquad.team05.web.review.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReviewSave {

    private final int rating;
    private final String comment;
}

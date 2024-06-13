package team07.airbnb.data.review.dto.response;

import team07.airbnb.data.user.dto.response.SimpleUserResponse;
import team07.airbnb.entity.ReplyEntity;

public record ReplyResponse(
        Long id,
        SimpleUserResponse writer,
        String content
) {

    public static ReplyResponse of(ReplyEntity reply) {
        return new ReplyResponse(
                reply.getId(),
                SimpleUserResponse.of(reply.getWriter()),
                reply.getContent()
        );
    }
}

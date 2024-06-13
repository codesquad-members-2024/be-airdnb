package com.team01.airdnb.comment.dto;

import com.team01.airdnb.user.dto.UserCommentResponse;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record CommentListResponse(
    double score,
    String content,
    LocalDateTime createdAt,
    UserCommentResponse user
) {

}

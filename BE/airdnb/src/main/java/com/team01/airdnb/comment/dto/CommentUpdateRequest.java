package com.team01.airdnb.comment.dto;

public record CommentUpdateRequest(
    String content,
    Double score
) {
}
package com.team01.airdnb.comment.dto;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.comment.Comment;
import com.team01.airdnb.user.User;

public record CommentRegisterRequest(
    Double score,
    String content,
    String user,
    Long accommodation
) {

  public Comment toEntity(User user, Accommodation accommodation) {
    return Comment.builder()
        .score(score)
        .content(content)
        .user(user)
        .accommodation(accommodation)
        .build();
  }
}

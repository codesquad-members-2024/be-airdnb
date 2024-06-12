package com.team01.airdnb.comment.dto;

import com.team01.airdnb.user.User;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentListResponse {

  Long id;
  Double score;
  String content;
  LocalDateTime createdAt;
  UserShow user;

  public CommentListResponse(Long id, Double score, String content, LocalDateTime createdAt,
      User user) {
    this.id = id;
    this.score = score;
    this.content = content;
    this.createdAt = createdAt;
    this.user = new UserShow(user);
  }

  @Getter
  static class UserShow {

    private String id;
    private String username;

    public UserShow(User user) {
      this.id = user.id;
      this.username = user.username;
    }
  }
}

package com.team01.airdnb.comment;

import com.team01.airdnb.comment.dto.CommentListResponse;
import com.team01.airdnb.user.UserService;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
  CommentRepository commentRepository;
  UserService userService;

  public CommentService(CommentRepository commentRepository, UserService userService) {
    this.commentRepository = commentRepository;
    this.userService = userService;
  }

  public List<Comment> findAmenityById(Long id) {
    return commentRepository.findByAccommodationId(id)
        .orElseThrow(() -> new NoSuchElementException("해당하는 코멘트가 존재하지 않습니다"));
  }

  public List<CommentListResponse> findAmenityByAmenityId(Long id) {
    List<Comment> comments = findAmenityById(id);
    List<CommentListResponse> commentListResponses = new ArrayList<>();
    for (Comment comment : comments) {
      commentListResponses.add(CommentListResponse.builder()
          .content(comment.getContent())
          .score(comment.getScore())
          .createdAt(comment.getCreatedAt())
          .user(userService.getCommentResponse(comment.getUser()))
          .build());
    }
    return commentListResponses;
  }

  public Double findAverageScoreByAccommodationId(Long id) {
    return commentRepository.findAverageScoreByAccommodationId(id);
  }
}

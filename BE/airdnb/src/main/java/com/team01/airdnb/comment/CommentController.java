package com.team01.airdnb.comment;

import com.team01.airdnb.comment.dto.CommentListResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/comment")
  public List<CommentListResponse> showAllComment() {
    List<CommentListResponse> comments = commentService.showAllComment(1L);
    return comments;
  }
}

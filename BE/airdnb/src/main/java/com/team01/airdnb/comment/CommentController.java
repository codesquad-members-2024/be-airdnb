package com.team01.airdnb.comment;

import com.team01.airdnb.comment.dto.CommentRegisterRequest;
import com.team01.airdnb.comment.dto.CommentShowResponse;
import com.team01.airdnb.comment.dto.CommentUpdateRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/comments/{commentId}")
  public List<CommentShowResponse> showAllComment(@PathVariable Long commentId) {
    return commentService.showAllComment(commentId);
  }

  @PutMapping("/comments/{commentId}")
  public CommentShowResponse updateComment(@PathVariable Long commentId,
      @RequestBody CommentUpdateRequest commentUpdateRequest) {
    return commentService.update(commentId, commentUpdateRequest);
  }

  @PostMapping("/comments")
  public CommentShowResponse registerComment(
      @RequestBody CommentRegisterRequest commentRegisterRequest) {
    return commentService.register(commentRegisterRequest);
  }

  @DeleteMapping("/comments/{commentId}")
  public void deleteComment(@PathVariable Long commentId) {
    commentService.delete(commentId);
  }
}

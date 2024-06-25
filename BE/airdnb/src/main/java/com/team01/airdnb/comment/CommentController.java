package com.team01.airdnb.comment;

import com.team01.airdnb.comment.dto.CommentRegisterRequest;
import com.team01.airdnb.comment.dto.CommentShowResponse;
import com.team01.airdnb.comment.dto.CommentUpdateRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  @Autowired
  private CommentService commentService;

  @GetMapping("/{commentId}")
  public List<CommentShowResponse> showAllComment(@PathVariable("commentId") Long commentId) {
    return commentService.showAllComment(commentId);
  }

  @PatchMapping("/{commentId}")
  public CommentShowResponse updateComment(@PathVariable("commentId") Long commentId,
      @RequestBody CommentUpdateRequest commentUpdateRequest) {
    return commentService.update(commentId, commentUpdateRequest);
  }

  @PostMapping
  public CommentShowResponse registerComment(
      @RequestBody CommentRegisterRequest commentRegisterRequest) {
    return commentService.register(commentRegisterRequest);
  }

  @DeleteMapping("/{commentId}")
  public void deleteComment(@PathVariable("commentId") Long commentId) {
    commentService.delete(commentId);
  }
}

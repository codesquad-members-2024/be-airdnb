package com.team01.airdnb.comment;

import com.team01.airdnb.comment.dto.CommentListResponse;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  /**
   * 해당 이슈에 달린 코멘트를 모두 조회합니다. 특별 예외 사항 댓글이 없는 경우 -> 비어 있는 리스트
   */
  public List<CommentListResponse> showAllComment(Long id) {
    return commentRepository.findAllByAccommodationId(id);
  }
}

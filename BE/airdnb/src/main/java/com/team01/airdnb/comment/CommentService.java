package com.team01.airdnb.comment;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.accommadation.AccommodationRepository;
import com.team01.airdnb.amenity.dto.CommentRegisterRequest;
import com.team01.airdnb.comment.dto.CommentShowResponse;
import com.team01.airdnb.comment.dto.CommentUpdateRequest;
import com.team01.airdnb.user.User;
import com.team01.airdnb.user.UserRepository;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CommentService {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private AccommodationRepository accommodationRepository;

  /**
   * 해당 이슈에 달린 코멘트를 모두 조회합니다. 특별 예외 사항 댓글이 없는 경우 -> 아마 비어있는 리스트가 갈 듯
   */
  public List<CommentShowResponse> showAllComment(Long id) {
    return commentRepository.findAllByAccommodationId(id);
  }

  /**
   * 특정 코멘트를 수정합니다. 수정하지 않는 부분(null)이 있는 경우는 체크해서 기존 값을 유지합니다.
   */
  @Transactional
  public CommentShowResponse update(Long id, CommentUpdateRequest commentUpdateRequest) {
    Comment target = commentRepository.findById(id).orElseThrow();
    target.update(commentUpdateRequest);
    log.debug("{} 코멘트가 수정되었습니다.", target.getId());

    return new CommentShowResponse(target.getId(), target.getScore(), target.getContent(),
        target.getCreatedAt(), target.getUser());
  }
}

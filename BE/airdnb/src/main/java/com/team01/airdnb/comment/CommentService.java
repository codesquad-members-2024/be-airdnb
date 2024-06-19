package com.team01.airdnb.comment;

import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.accommadation.AccommodationRepository;
import com.team01.airdnb.comment.dto.CommentRegisterRequest;
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

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  private final AccommodationRepository accommodationRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository, UserRepository userRepository,
      AccommodationRepository accommodationRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.accommodationRepository = accommodationRepository;
  }

  /**
   * 새로운 코멘트를 등록합니다.
   * @param commentRegisterRequest 코멘트 등록 정보가 담긴 dto
   * @return 등록된 코멘트를 변환한 조회 dto
   */
  public CommentShowResponse register(CommentRegisterRequest commentRegisterRequest) {
    User commentWriter = userRepository.findById(commentRegisterRequest.user()).orElseThrow();
    Accommodation accommodation = accommodationRepository.findById(
        commentRegisterRequest.accommodation()).orElseThrow();

    Comment newComment = commentRegisterRequest.toEntity(commentWriter, accommodation);
    log.debug("{} 코멘트가 등록되었습니다.", newComment.getId());
    commentRepository.save(newComment);
    return new CommentShowResponse(newComment.getId(), newComment.getScore(), newComment.getContent(),
        newComment.getCreatedAt(), newComment.getUser());
  }

  /**
   * 해당 이슈에 달린 코멘트를 모두 조회합니다.
   * 댓글이 없는 경우 비어있는 리스트가 생성됩니다.
   * @param  id 숙소 id
   * @return 코멘트 조회 dto 목록
   */
  public List<CommentShowResponse> showAllComment(Long id) {
    findExistAccommodation(id);
    return commentRepository.findAllByAccommodationId(id);
  }

  /**
   * 특정 코멘트를 삭제합니다.
   * @param id 숙소 id
   */
  public void delete(Long id) {
    Comment target = commentRepository.findById(id).orElseThrow();
    commentRepository.delete(target);
  }

  /**
   * 특정 코멘트를 수정합니다. 수정하지 않는 부분(null)이 있는 경우는 체크해서 기존 값을 유지합니다.
   * @param id 숙소 id
   * @param commentUpdateRequest 업데이트 내역이 담겨 있는 dto
   * @return 수정된 코멘트를 변환한 dto
   */
  @Transactional
  public CommentShowResponse update(Long id, CommentUpdateRequest commentUpdateRequest) {
    Comment target = commentRepository.findById(id).orElseThrow();
    target.update(commentUpdateRequest);
    log.debug("{} 코멘트가 수정되었습니다.", target.getId());

    return new CommentShowResponse(target.getId(), target.getScore(), target.getContent(),
        target.getCreatedAt(), target.getUser());
  }

  public Double findAverageScoreByAccommodationId(Long id) {
    return commentRepository.findAverageScoreByAccommodationId(id);
  }

  /**
   * 존재하지 않는 숙소의 코멘트를 조회하려고 하는 경우를 체크합니다.
   * @param id
   */
  private void findExistAccommodation(Long id) {
    accommodationRepository.findById(id).orElseThrow();
  }
}

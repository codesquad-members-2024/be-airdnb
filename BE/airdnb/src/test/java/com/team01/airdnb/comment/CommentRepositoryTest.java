package com.team01.airdnb.comment;


import com.team01.airdnb.accommadation.Accommodation;
import com.team01.airdnb.comment.dto.CommentShowResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Transactional
class CommentRepositoryTest {

  @Autowired
  private CommentRepository commentRepository;
  @PersistenceContext
  private EntityManager entityManager;

  @Test
  void 코멘트리스트를반환한다() {
    Accommodation accommodation = Accommodation.builder()
        .title("1번 숙소")
        .price(30000L)
        .address("서울특별시 강남구")
        .build();

    Comment comment = Comment.builder().content("댓글 내용").score(3.6).accommodation(accommodation)
        .build();
    Comment comment2 = Comment.builder().content("댓글2 내용").score(4.6).accommodation(accommodation)
        .build();

    entityManager.persist(comment);
    entityManager.persist(comment2);
    entityManager.flush();
    entityManager.clear();

    Long accommodationId = accommodation.getId();
    List<CommentShowResponse> result = commentRepository.findAllByAccommodationId(
        accommodationId);
    System.out.println(result);
  }
}

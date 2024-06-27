package codesquad.team05.domain.hashtag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    boolean existsByContent(String content);

    Optional<Hashtag> findByContent(String content);
}

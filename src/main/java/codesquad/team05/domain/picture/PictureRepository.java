package codesquad.team05.domain.picture;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    boolean existsByUrl(String url);
}

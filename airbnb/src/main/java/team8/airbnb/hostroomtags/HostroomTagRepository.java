package team8.airbnb.hostroomtags;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostroomTagRepository extends JpaRepository<HostroomTag, HostroomTagId> {

  List<HostroomTag> findByTagId(Long tagId);

  List<HostroomTag> findByHostroomId(Long hostroomId);
}

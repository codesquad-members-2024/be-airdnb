package team8.airbnb.hostroomitems;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostroomItemsRepository extends JpaRepository<HostroomItems, HostroomItemsId> {
  List<HostroomItems> findByHostroomId(Long hostroomId);
}

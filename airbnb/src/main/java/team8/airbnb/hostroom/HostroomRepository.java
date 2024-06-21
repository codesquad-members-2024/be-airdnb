package team8.airbnb.hostroom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HostroomRepository extends JpaRepository<Hostroom, Long> {

}
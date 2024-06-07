package team8.airbnb.user;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByEmail(String email);

  User findByPhoneNumber(String phoneNumber);

  User findByUsername(String username);
}
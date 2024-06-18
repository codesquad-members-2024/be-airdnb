package team8.airbnb.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team8.airbnb.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  List<User> findByEmail(String email);

  User findByPhoneNumber(String phoneNumber);

  User findByUsername(String username);
}
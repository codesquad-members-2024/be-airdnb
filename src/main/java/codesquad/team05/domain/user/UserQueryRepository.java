package codesquad.team05.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserQueryRepository extends JpaRepository<User, String> {
}

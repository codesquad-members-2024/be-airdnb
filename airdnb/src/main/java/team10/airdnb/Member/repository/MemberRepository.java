package team10.airdnb.Member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.Member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);

}

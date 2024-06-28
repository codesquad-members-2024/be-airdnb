package team10.airdnb.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.member.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByRefreshToken(String refreshToken);

}

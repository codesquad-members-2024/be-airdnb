package codesquad.airdnb.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OAuthRepository extends JpaRepository<OAuth, Long> {
    OAuth findOauthByMemberId(Long memberId);
}

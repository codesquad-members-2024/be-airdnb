package codesquad.airdnb.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByLoginId(String loginId);

    Member findMemberByLoginId(String loginId);

    @Transactional
    @Modifying
    @Query("UPDATE Member AS m SET m.refreshToken = :refreshToken WHERE m.loginId = :loginId")
    void updateRefreshToken(String loginId, String refreshToken);
}

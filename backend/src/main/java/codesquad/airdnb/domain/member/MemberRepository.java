package codesquad.airdnb.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsMemberByAccountName(String accountName);

    Member findMemberByAccountName(String accountName);

    @Transactional
    @Modifying
    @Query("UPDATE Member AS m SET m.refreshToken = :refreshToken WHERE m.accountName = :accountName")
    void updateRefreshToken(String accountName, String refreshToken);
}

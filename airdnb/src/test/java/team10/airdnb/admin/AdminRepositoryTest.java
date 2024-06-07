package team10.airdnb.admin;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import team10.airdnb.admin.repository.AdminRepository;
import team10.airdnb.admin.entity.Admin;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@AllArgsConstructor
class AdminRepositoryTest {

    private final AdminRepository adminRepository;

    @Test
    void saveAdmin() {
        Admin admin = Admin.builder()
                .adminId("admin@naver.com")
                .password("1234")
                .build();

        adminRepository.save(admin);
    }
}

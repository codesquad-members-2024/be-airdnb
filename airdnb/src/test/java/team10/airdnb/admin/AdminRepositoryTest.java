package team10.airdnb.admin;

import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import team10.airdnb.admin.repository.AdminRepository;
import team10.airdnb.admin.entity.Admin;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    @Test
    void saveAdmin() {
        Admin admin = Admin.builder()
                .adminId("admin@naver.com")
                .password("1234")
                .build();

        adminRepository.save(admin);
    }
}

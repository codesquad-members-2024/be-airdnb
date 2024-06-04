package team10.airdnb.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team10.airdnb.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, String> {
}

package team10.airdnb.admin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "admin")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Admin {
    @Id
    @Column(name = "admin_id", length = 50)
    private String adminId;
    @Column(name = "password", nullable = false, length = 30)
    private String password;

}

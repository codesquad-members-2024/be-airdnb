package team07.airbnb.domain.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.user.enums.Role;

import java.io.Serializable;

@Getter
@Entity
@Table(name = "USERS")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String picture;

    @NotNull
    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @NotNull
    private String registrationId;


    public UserEntity update(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;

        return this;
    }

    public String stringRole() {
        return this.role.getKey();
    }
}

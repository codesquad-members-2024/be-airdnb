package team07.airbnb.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USERS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String picture;

    private String name;

    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String registrationId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<LikeEntity> favorites = new ArrayList<>();

    public UserEntity updateInfo(String name, String email, String picture) {
        this.name = name;
        this.email = email;
        this.picture = picture;

        return this;
    }

    public UserEntity addFavorite(ProductEntity product) {
        favorites.add(LikeEntity.from(this, product));

        return this;
    }

    public UserEntity removeFavorite(ProductEntity product) {
        favorites.remove(LikeEntity.from(this, product));

        return this;
    }

    public String stringRole() {
        return this.role.getKey();
    }

    public void setRoleToHost() {
        this.role = Role.HOST;
    }

    public void setRoleToUser() {
        this.role = Role.USER;
    }

    public static UserEntity ofToken(TokenUserInfo tokenUserInfo) {
        return UserEntity.builder()
                .id(tokenUserInfo.id())
                .name(tokenUserInfo.name())
                .picture(tokenUserInfo.profileImg())
                .role(tokenUserInfo.role())
                .build();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserEntity) {
            return this.id.equals(((UserEntity) obj).id);
        }
        return false;
    }
}

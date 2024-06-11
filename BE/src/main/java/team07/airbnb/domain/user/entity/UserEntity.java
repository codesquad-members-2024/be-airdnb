package team07.airbnb.domain.user.entity;

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
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team07.airbnb.domain.BaseEntity;
import team07.airbnb.domain.booking.entity.BookingEntity;
import team07.airbnb.domain.product.entity.ProductEntity;
import team07.airbnb.domain.user.enums.Role;

import java.util.List;

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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<LikeEntity> favorites;

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

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UserEntity) {
            return this.id.equals(((UserEntity) obj).id);
        }
        return false;
    }
}

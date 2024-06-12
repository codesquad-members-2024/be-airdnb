package team07.airbnb.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Table(name = "USER_LIKES_PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    UserEntity user;
    @ManyToOne
    ProductEntity product;

    public static LikeEntity from(UserEntity user, ProductEntity product) {
        return LikeEntity.builder()
                .user(user)
                .product(product)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LikeEntity that = (LikeEntity) o;

        if (!Objects.equals(user, that.user)) return false;
        return Objects.equals(product, that.product);
    }
}

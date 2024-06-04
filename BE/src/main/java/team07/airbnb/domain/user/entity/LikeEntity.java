package team07.airbnb.domain.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import team07.airbnb.domain.product.ProductEntity;

@Entity
@Table(name = "USER_LIKES_PRODUCT")
public class LikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    UserEntity user;
    @ManyToOne
    ProductEntity product;
}

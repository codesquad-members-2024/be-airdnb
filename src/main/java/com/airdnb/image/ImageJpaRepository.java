package com.airdnb.image;

import com.airdnb.image.entity.Image;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageJpaRepository implements ImageRepository {
    private final EntityManager em;

    @Override
    public Optional<Image> findById(Long id) {
        Image image = em.find(Image.class, id);
        return Optional.ofNullable(image);
    }
}

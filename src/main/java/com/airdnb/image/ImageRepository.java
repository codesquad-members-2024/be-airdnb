package com.airdnb.image;

import com.airdnb.image.entity.Image;
import java.util.Optional;

public interface ImageRepository {
    Optional<Image> findById(Long id);
}

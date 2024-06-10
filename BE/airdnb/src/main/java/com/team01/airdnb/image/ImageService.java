package com.team01.airdnb.image;

import org.springframework.stereotype.Service;

@Service
public class ImageService {
    ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public void save(Image image) {
        imageRepository.save(image);
    }
}

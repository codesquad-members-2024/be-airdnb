package com.team01.airdnb.image;

import com.team01.airdnb.image.dto.ImageListResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ImageService {
    private final ImageRepository imageRepository;

    @Autowired
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<ImageListResponse> findByAccommodationId(Long id) {
        List<Image> images = getImage(id);
        List<ImageListResponse> imageListResponses = new ArrayList<>();

        for (Image image : images) {
            imageListResponses.add(ImageListResponse.builder()
                .imagePath(image.getImagePath())
                .build());
        }
        return imageListResponses;
    }

    public List<Image> getImage(Long id) {
        return imageRepository.findByAccommodationId(id)
            .orElseThrow(() -> new NoSuchElementException("해당하는 어메니티가 존재하지 않습니다"));
    }
}

package codesquad.team05.service;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.util.AccommodationMapper;
import codesquad.team05.web.accommodation.dto.request.AccommodationSaveServiceRequest;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdateServiceRequest;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccommodationService {

    private final AmazonS3Client amazonS3Client;
    private final AccommodationRepository accommodationRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    public Long register(AccommodationSaveServiceRequest serviceRequest, List<String> pictures) {
        Accommodation accommodation = AccommodationMapper.toEntity(serviceRequest);
        List<Picture> picture = toPicture(pictures, accommodation);
        accommodation.setPictures(picture);
        return accommodationRepository.save(accommodation).getId();
    }

    public String uploadFile(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }
        String filename = file.getOriginalFilename();
        String fileUrl = s3Url + filename;

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        objectMetadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, filename, file.getInputStream(), objectMetadata);

        return fileUrl;
    }

    @Transactional(readOnly = true)
    public AccommodationResponse getAccommodationById(Long accommodationId) {
        return AccommodationMapper.toResponse(accommodationRepository.findById(accommodationId).orElseThrow());
    }

    public void updateAccommodation(
            Long accommodationId,
            AccommodationUpdateServiceRequest newAccommodation,
            List<String> url
    ) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        accommodation.update(
                newAccommodation.getName(),
                newAccommodation.getPrice(),
                newAccommodation.getAddress(),
                newAccommodation.getMaxCapacity(),
                newAccommodation.getRoomCount(),
                newAccommodation.getBedCount(),
                newAccommodation.getDescription(),
                newAccommodation.getAmenity()
        );
    }

    private List<Picture> toPicture(List<String> pictures, Accommodation accommodation) {
        return pictures.stream().map(url -> {
            Picture picture = new Picture(url);
            picture.setAccommodation(accommodation);
            return picture;
        }).toList();
    }

    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }
}

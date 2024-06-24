package codesquad.team05.service;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.domain.picture.PictureRepository;
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
    private final PictureRepository pictureRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    public Long register(AccommodationSaveServiceRequest saveServiceRequest) {
        Accommodation accommodation = AccommodationMapper.toEntity(saveServiceRequest);
        Long accommodationId = accommodationRepository.save(accommodation).getId();
        List<MultipartFile> files = saveServiceRequest.getFiles();
        if (files != null && !files.isEmpty() && !files.stream().allMatch(MultipartFile::isEmpty)) {
            List<String> pictureUrls = files.stream()
                    .map(url -> uploadFileToS3(url, accommodationId))
                    .toList();
            savePicture(pictureUrls, accommodation);
        }
        return accommodationId;
    }

    @Transactional(readOnly = true)
    public AccommodationResponse getAccommodationById(Long accommodationId) {
        return AccommodationMapper.toResponse(accommodationRepository.findById(accommodationId).orElseThrow());
    }

    public void updateAccommodation(
            Long accommodationId,
            AccommodationUpdateServiceRequest updateServiceRequest
    ) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        accommodation.update(
                updateServiceRequest.getName(),
                updateServiceRequest.getPrice(),
                updateServiceRequest.getAddress(),
                updateServiceRequest.getMaxCapacity(),
                updateServiceRequest.getRoomCount(),
                updateServiceRequest.getBedCount(),
                updateServiceRequest.getDescription(),
                updateServiceRequest.getAmenity(),
                updateServiceRequest.getAccommodationType()
        );
        List<String> pictureUrls = updateServiceRequest.getFiles().stream()
                .map(url -> uploadFileToS3(url, accommodationId))
                .toList();
        savePicture(pictureUrls, accommodation);
    }

    private String uploadFileToS3(MultipartFile file, Long accommodationId) {
        try {
            String filename = file.getOriginalFilename();
            String fileUrl = s3Url + accommodationId + "/" + filename;

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(file.getContentType());
            objectMetadata.setContentLength(file.getSize());
            amazonS3Client.putObject(bucket, filename, file.getInputStream(), objectMetadata);

            return fileUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void savePicture(List<String> pictures, Accommodation accommodation) {
        pictures.forEach(url -> {
            if (!pictureRepository.existsByUrl(url)) {
                pictureRepository.save(new Picture(url, accommodation));
            }
        });
    }

    public void delete(Long id) {
        accommodationRepository.deleteById(id);
    }
}

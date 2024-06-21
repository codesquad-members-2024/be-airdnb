package codesquad.team05.service;

import codesquad.team05.domain.accomodation.Accommodation;
import codesquad.team05.domain.accomodation.AccommodationRepository;
import codesquad.team05.domain.picture.Picture;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdate;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
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


    public Long register(AccommodationSave form, List<String> pictures) {

        Accommodation accommodation = new Accommodation(form.getName(), form.getPrice(), form.getAddress(), form.getMaxCapacity(), form.getRoomCount(),
                form.getBedCount(), form.getDescription(), form.getAmenity(), form.getHostId(), form.getAccommodationType());

        List<Picture> picture = toPicture(pictures, accommodation);
        accommodation.setPictures(picture);

        Accommodation saved = accommodationRepository.save(accommodation);
        return saved.getId();
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
    public Accommodation getAccommodation(Long accommodationId) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        return accommodation;
    }

    public void updateAccommodation(Long accommodationId, AccommodationUpdate newAccommodation, List<String> url) {
        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        accommodation.update(newAccommodation);
    }

    private List<Picture> toPicture(List<String> pictures, Accommodation accommodation) {

        if (pictures == null || pictures.isEmpty()) {
            return Collections.emptyList();
        }

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

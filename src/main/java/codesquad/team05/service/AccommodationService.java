package codesquad.team05.service;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.accommodation.accommodationhashtag.AccommodationHashtag;
import codesquad.team05.domain.accommodation.accommodationhashtag.AccommodationHashtagId;
import codesquad.team05.domain.accommodation.accommodationhashtag.AccommodationHashtagRepository;
import codesquad.team05.domain.hashtag.Hashtag;
import codesquad.team05.domain.hashtag.HashtagRepository;
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
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AccommodationService {

    private final AmazonS3Client amazonS3Client;
    private final AccommodationRepository accommodationRepository;
    private final PictureRepository pictureRepository;
    private final HashtagRepository hashtagRepository;
    private final AccommodationHashtagRepository accommodationHashtagRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.url}")
    private String s3Url;

    public Long register(AccommodationSaveServiceRequest saveServiceRequest) {
        Accommodation accommodation = AccommodationMapper.toEntity(saveServiceRequest);

        /**
         * Request Data로 넘어온 hashtagContents 활용
         * Accommodation 저장 (AccommodationHashtag와는 CascadeType.PERSIST가 걸려있음)
         */
        List<Hashtag> hashtags = saveHashtags(saveServiceRequest.getHashtagContents());
        setAccommodationHashtag(hashtags, accommodation);

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

    /**
     * hashtagContent가 DB에 존재하지 않을 때만 저장하고 해당 객체를 List에 추가
     * 이미 존재하면 해당 content로 객체를 찾아서 List에 추가
     */
    private List<Hashtag> saveHashtags(List<String> hashtagContents) {
        List<Hashtag> hashtags = new ArrayList<>();
        hashtagContents.forEach(
                hashtagContent -> {
                    if (!hashtagRepository.existsByContent(hashtagContent)) {
                        hashtags.add(hashtagRepository.save(new Hashtag(hashtagContent)));
                    } else {
                        hashtags.add(hashtagRepository.findByContent(hashtagContent).get());
                    }
                });
        return hashtags;
    }

    /**
     * accommodationHashtagId(PK)가 존재하지 않을 경우에만 List에 추가
     * 이미 존재하면 해당 accommodationHashtagId로 객체를 찾아서 List에 추가
     */
    private void setAccommodationHashtag(List<Hashtag> hashtags, Accommodation accommodation) {
        hashtags.forEach(
                hashtag -> {
                    AccommodationHashtagId accommodationHashtagId = new AccommodationHashtagId(accommodation.getId(), hashtag.getId());
                    if (!accommodationHashtagRepository.existsByAccommodationHashtagId(accommodationHashtagId)) {
                        accommodation.addAccommodationHashtag(
                                new AccommodationHashtag(accommodation, hashtag)
                        );
                    } else {
                        accommodation.addAccommodationHashtag(
                                accommodationHashtagRepository.findByAccommodationHashtagId(accommodationHashtagId).get()
                        );
                    }
                }
        );
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

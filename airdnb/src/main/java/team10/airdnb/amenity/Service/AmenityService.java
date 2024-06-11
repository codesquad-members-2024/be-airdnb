package team10.airdnb.amenity.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team10.airdnb.amenity.Controller.request.AmenityRequest;
import team10.airdnb.amenity.entity.Amenity;
import team10.airdnb.amenity.exception.AmenityIdNotFoundException;
import team10.airdnb.amenity.exception.AmenityNameDuplicateException;
import team10.airdnb.amenity.repository.AmenityRepository;
import team10.airdnb.error.ErrorCode;

import java.util.List;

@Service
public class AmenityService {
    private final AmenityRepository amenityRepository;

    @Autowired
    public AmenityService(AmenityRepository amenityRepository) {
        this.amenityRepository = amenityRepository;
    }

    // 새로운 어메니티 생성
    public Amenity saveAmenity(AmenityRequest request) {
        validateDuplicateAmenity(request.name());
        return amenityRepository.save(request.toEntity());
    }

    // 어메니티 수정
    public Amenity updateAmenity(long amenityId, AmenityRequest request) {
        validateDuplicateAmenity(request.name());
        Amenity amenity = getAmenityById(amenityId);
        amenity.updateName(request.name());
        return amenityRepository.save(amenity);
    }

    // 어메니티 삭제
    public Amenity deleteAmenity(long amenityId) {
        Amenity amenity = getAmenityById(amenityId);
        amenityRepository.delete(amenity);
        return amenity;
    }

    public List<Amenity> getAllAmenity() {
        return amenityRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    private Amenity getAmenityById(long amenityId) {
        return amenityRepository.findById(amenityId)
                .orElseThrow(() -> new AmenityIdNotFoundException(ErrorCode.AMENITY_TYPE_NOT_EXISTS));
    }

    private void validateDuplicateAmenity(String inputAmenity) {
        amenityRepository.findByName(inputAmenity)
                .ifPresent(amenity -> {
                    throw new AmenityNameDuplicateException(ErrorCode.ALREADY_SAVED_AMENITY);
                });
    }
}

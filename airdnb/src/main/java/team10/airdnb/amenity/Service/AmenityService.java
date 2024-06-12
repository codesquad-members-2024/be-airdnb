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

    public Amenity saveAmenity(AmenityRequest request) {
        validateDuplicateAmenity(request.name());
        return amenityRepository.save(request.toEntity());
    }

    public Amenity updateAmenity(long amenityId, AmenityRequest request) {
        validateDuplicateAmenity(request.name());
        Amenity amenity = getAmenityById(amenityId);
        amenity.updateName(request.name());
        return amenityRepository.save(amenity);
    }

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
                .orElseThrow(AmenityIdNotFoundException::new);
    }

    private void validateDuplicateAmenity(String inputAmenity) {
        amenityRepository.findByName(inputAmenity)
                .ifPresent(amenity -> {
                    throw new AmenityNameDuplicateException();
                });
    }
}

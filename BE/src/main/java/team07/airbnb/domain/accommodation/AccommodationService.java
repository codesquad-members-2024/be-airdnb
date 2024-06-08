package team07.airbnb.domain.accommodation;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.util.GeometryHelper;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final GeometryHelper geometryHelper;
    private final AccommodationRepository accommodationRepository;

    public List<AccommodationEntity> findNearbyAccommodations(double longitude, double latitude, double distance) {
        Point center = geometryHelper.getPoint(longitude, latitude);

        return accommodationRepository.findByLocationWithinDistance(center, distance);
    }

    public List<AccommodationEntity> findAllAccommodations() {
        return accommodationRepository.findAll();
    }

    public AccommodationEntity addAccommodation(AccommodationEntity newAccommodation) {
        return accommodationRepository.save(newAccommodation);
    }

    public AccommodationEntity findById(long id) {
        return getAccommodationById(id);
    }

    public void deleteById(long id) {
        try {
            accommodationRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 숙소%d 를 삭제할 수 없습니다.".formatted(id));
        }
    }


    private AccommodationEntity getAccommodationById(long id) throws NoSuchElementException {
        return accommodationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 숙소 %d".formatted(id)));
    }

    public boolean isAvailableOccupancy(AccommodationEntity accommodation, Integer headCount) {
        return accommodation.getRoomInformation().getMaxOccupancy() >= headCount;
    }
}

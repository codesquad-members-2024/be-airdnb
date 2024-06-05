package team07.airbnb.domain.accommodation;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.util.GeometryHelper;

import java.util.List;

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
}

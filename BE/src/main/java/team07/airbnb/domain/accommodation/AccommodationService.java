package team07.airbnb.domain.accommodation;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.common.auth.exception.UnAuthorizedException;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.enums.Role;
import team07.airbnb.domain.user.service.UserService;
import team07.airbnb.common.util.GeometryHelper;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final GeometryHelper geometryHelper;
    private final AccommodationRepository accommodationRepository;
    private final UserService userService;

    public List<AccommodationEntity> findNearbyAccommodations(double longitude, double latitude, double distance) {
        Point center = geometryHelper.getPoint(longitude, latitude);

        return accommodationRepository.findByLocationWithinDistance(center, distance);
    }

    public List<AccommodationEntity> findAllAccommodations() {
        return accommodationRepository.findAll();
    }

    @Transactional
    public AccommodationEntity addAccommodation(AccommodationEntity newAccommodation) {
        if (newAccommodation.getHost().getRole() == Role.USER) {
            userService.userGrantToHost(newAccommodation.getHost());
        }

        return accommodationRepository.save(newAccommodation);
    }

    public AccommodationEntity findById(long id) {
        return getAccommodationById(id);
    }

    @Transactional
    public void deleteById(long id, UserEntity user) {
        if(!getHostIdById(id).equals(user.getId())) throw new UnAuthorizedException();

        try {
            accommodationRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("존재하지 않는 숙소%d 를 삭제할 수 없습니다.".formatted(id));
        }

        boolean hostHasAcc = accommodationRepository.findAccommodationEntityByHost(user);
        if (!hostHasAcc) userService.userGrantToUser(user); // 남은 숙소가 없다면 호스트에서 일반 유저로 변경
    }

    private AccommodationEntity getAccommodationById(long id) throws NoSuchElementException {
        return accommodationRepository.findById(id).orElseThrow(() -> new NoSuchElementException("존재하지 않는 숙소 %d".formatted(id)));
    }

    public boolean isAvailableOccupancy(AccommodationEntity accommodation, Integer headCount) {
        return accommodation.getRoomInformation().getMaxOccupancy() >= headCount;
    }

    public UserEntity getHostIdById(Long accId) {
        return accommodationRepository.findById(accId).orElseThrow(() -> new NoSuchElementException("존재하지 않는 숙소입니다")).getHost();
    }
}

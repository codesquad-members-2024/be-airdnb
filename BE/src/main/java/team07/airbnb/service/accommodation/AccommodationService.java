package team07.airbnb.service.accommodation;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team07.airbnb.common.util.DateHelper;
import team07.airbnb.common.util.GeometryHelper;
import team07.airbnb.data.accommodation.dto.request.AccommodationFilterDTO;
import team07.airbnb.data.accommodation.enums.AccommodationType;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.Pictures;
import team07.airbnb.entity.ProductEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.entity.embed.AccommodationLocation;
import team07.airbnb.entity.embed.RoomInformation;
import team07.airbnb.exception.auth.UnAuthorizedException;
import team07.airbnb.exception.not_found.AccommodationNotFoundException;
import team07.airbnb.repository.AccommodationRepository;
import team07.airbnb.service.user.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final GeometryHelper geometryHelper;
    private final AccommodationRepository accommodationRepository;
    private final UserService userService;
    private final DateHelper dateHelper;

    @Transactional
    public AccommodationEntity addAccommodation(AccommodationEntity newAccommodation) {
        if (newAccommodation.getHost().getRole() == Role.USER) {
            userService.userGrantToHost(newAccommodation.getHost());
        }

        return accommodationRepository.save(newAccommodation);
    }

    @Transactional
    public void deleteById(long id, UserEntity user) {
        accommodationRepository.delete(authorize(id, user.getId()));

        boolean hostHasAcc = accommodationRepository.findAccommodationEntityByHost(user);
        if (!hostHasAcc) userService.userGrantToUser(user); // 남은 숙소가 없다면 호스트에서 일반 유저로 변경
    }

    @Transactional
    public AccommodationEntity updateAccommodation(Long id, RoomInformation roomInformation, Long userId) {
        AccommodationEntity accommodation = authorize(id, userId);
        accommodation.updateRoomInfo(roomInformation);
        return accommodationRepository.save(accommodation);
    }

    @Transactional
    public AccommodationEntity updateAccommodation(Long id,String name, String description, Long userId) {
        AccommodationEntity accommodation = authorize(id, userId);
        accommodation.updateDescription(name, description);
        return accommodationRepository.save(accommodation);
    }

    @Transactional
    public AccommodationEntity updateAccommodation(Long id,
                                                   AccommodationType type,
                                                   AccommodationLocation address,
                                                   int basePricePerDay,
                                                   Long userId)
    {
        AccommodationEntity accommodation = authorize(id, userId);
        accommodation.updateBaseInfo(type, address, basePricePerDay);
        return accommodationRepository.save(accommodation);
    }

    @Transactional
    public AccommodationEntity updateAccommodation(Long id, List<String> pictures, Long userId) {
        AccommodationEntity accommodation = authorize(id, userId);
        accommodation.updatePictures(
                pictures.stream().map(url -> new Pictures(accommodation, url)).toList()
        );

        return accommodationRepository.save(accommodation);
    }


    public AccommodationEntity findById(long id) {
        return getAccommodationById(id);
    }

    public List<AccommodationEntity> findNearbyAccommodations(double longitude, double latitude, double distance) {
        Point center = geometryHelper.getPoint(longitude, latitude);

        return accommodationRepository.findByLocationWithinDistance(center, GeometryHelper.toKilometer(distance));
    }

    public List<AccommodationEntity> findAllAccommodations() {
        return accommodationRepository.findAll();
    }

    public List<ProductEntity> findAvailableProductsInMonth(LocalDate request, Long accommodationId) {
        int year = request.getYear();
        int month = request.getMonthValue();

        return getAccommodationById(accommodationId).getOpenProducts().stream().filter(
                product -> dateHelper.isDateInMonthYear(year, month, product.getDate())
        ).toList();
    }

    public List<AccommodationEntity> findByHost(UserEntity user) {
        return accommodationRepository.findAllByHost(user);
    }

    public boolean isAvailableOccupancy(AccommodationEntity accommodation, Integer headCount) {
        return accommodation.getRoomInformation().getMaxOccupancy() >= headCount;
    }

    public UserEntity getHostIdById(Long accId) {
        return getAccommodationById(accId).getHost();
    }

    private AccommodationEntity getAccommodationById(long id) throws NoSuchElementException {
        return accommodationRepository.findById(id).orElseThrow(() -> new AccommodationNotFoundException(id));
    }

    private AccommodationEntity authorize(long id, long userId) {
        AccommodationEntity accommodation = getAccommodationById(id);
        if (!accommodation.getHost().getId().equals(id)) throw new UnAuthorizedException(this.getClass(), userId);

        return accommodation;
    }

    public List<AccommodationEntity> findWithFilter(AccommodationFilterDTO filter) {
        return null;
    }
}

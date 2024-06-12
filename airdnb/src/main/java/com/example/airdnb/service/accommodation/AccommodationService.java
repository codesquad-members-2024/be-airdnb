package com.example.airdnb.service.accommodation;

import com.example.airdnb.domain.accommodation.Accommodation;
import com.example.airdnb.domain.accommodation.Address;
import com.example.airdnb.domain.accommodation.Image;
import com.example.airdnb.domain.user.User;
import com.example.airdnb.domain.user.User.Role;
import com.example.airdnb.dto.accommodation.AccommodationCreationRequest;
import com.example.airdnb.dto.accommodation.AccommodationResponse;
import com.example.airdnb.repository.accommodation.AccommodationRepository;
import com.example.airdnb.repository.user.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final UserRepository userRepository;

    public List<AccommodationResponse> getAccommodationList() { // 검색 조건에 따른 결과를 가져오도록 변경 해야함

        List<Accommodation> accommodations = accommodationRepository.findAll();

        return accommodations.stream()
                .map(AccommodationResponse::of)
                .toList();
    }

    public Accommodation createNewAccommodation(AccommodationCreationRequest request) {
        User saveUser = userRepository.save(new User("abc", "bde", "a", Role.HOST));
        User host = findUserById(saveUser.getId());
        List<Image> images = getImagesFromRequest(request);

        Address address = buildAddress(request);

        Accommodation accommodation = Accommodation.builder()
                .address(address)// 임시 코드
                .user(host)
                .name(request.name())
                .pricePerNight(request.pricePerNight())
                .description(request.description())
                .maxGuests(request.maxGuests())
                .images(images)
                .build();

        return accommodationRepository.save(accommodation);
    }

    private Address buildAddress(AccommodationCreationRequest request) {
        //todo 주소 값들로 부터 좌표 값을 구하는 기능 추가 예정
        GeometryFactory gf = new GeometryFactory();
        Point point = gf.createPoint(
                new Coordinate(
                        request.address().latitude(),
                        request.address().longitude()
                )); // 임시 코드

        return new Address(
                request.address().country(),
                request.address().state(),
                request.address().city(),
                request.address().detail(),
                point
        );
    }

    private List<Image> getImagesFromRequest(AccommodationCreationRequest request) {
        return request.imageUrls().stream()
                .map(Image::new)
                .toList();
    }

    private User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(RuntimeException::new);
    }
}

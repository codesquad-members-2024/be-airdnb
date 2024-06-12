package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.accommodation.dto.request.AccommodationCreateRequest;
import team07.airbnb.data.accommodation.dto.response.AccommodationListResponse;
import team07.airbnb.data.user.dto.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.user.UserService;

import java.util.List;

@Tag(name = "숙소")
@RequestMapping("/accommodation")
@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;
    private final UserService userService;

    @Operation(summary = "모든 숙소 조회", description = "스쿼드비엔비에 등록된 모든 숙소를 조회합니다.")
    @GetMapping
    public List<AccommodationEntity> findAll() {
        return accommodationService.findAllAccommodations();
    }

    @GetMapping("/location")
    public List<AccommodationListResponse> findNeighbor(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance) {

        return accommodationService.findNearbyAccommodations(longitude, latitude, distance * 1000)
                .stream()
                .map(AccommodationListResponse::of)
                .toList();
    }

    @PostMapping
    @Authenticated(Role.USER)
    public AccommodationListResponse createAccommodation(@RequestBody AccommodationCreateRequest createRequest, TokenUserInfo user) {
        return AccommodationListResponse.of(accommodationService.addAccommodation(
                createRequest.toEntity(userService.getCompleteUser(user))
        ));
    }

    @GetMapping("/{id}")
    public AccommodationEntity accommodationDetail(@PathVariable long id) {
        return accommodationService.findById(id);
    }

    @DeleteMapping("/{id}")
    @Authenticated(Role.HOST)
    public void deleteAccommodation(@PathVariable long id, TokenUserInfo user) {
        accommodationService.deleteById(id, userService.getCompleteUser(user));
    }
}

package team07.airbnb.domain.accommodation;

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
import team07.airbnb.domain.accommodation.dto.AccommodationCreateRequest;
import team07.airbnb.domain.accommodation.dto.AccommodationListResponse;
import team07.airbnb.domain.accommodation.entity.AccommodationEntity;
import team07.airbnb.domain.auth.aop.Authenticated;
import team07.airbnb.domain.user.entity.UserEntity;
import team07.airbnb.domain.user.enums.Role;

import java.util.List;

@RequestMapping("/accommodation")
@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;


    @GetMapping
    @Tag(name = "User API")
    @Operation(summary = "모든 숙소 조회", description = "스쿼드비엔비에 등록된 모든 숙소를 조회합니다.")
    public List<AccommodationEntity> findAll() {
        return accommodationService.findAllAccommodations();
    }

    @GetMapping("/location")
    public List<AccommodationListResponse> findNeighbor(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance) {

        return accommodationService.findNearbyAccommodations(longitude, latitude, distance)
                .stream()
                .map(AccommodationListResponse::of)
                .toList();
    }

    @PostMapping
    @Authenticated(Role.USER)
    public void createAccommodation(@RequestBody AccommodationCreateRequest createRequest, UserEntity user) {
        accommodationService.addAccommodation(
                createRequest.toEntity(user)
        );
    }

    @GetMapping("/{id}")
    public AccommodationEntity accommodationDetail(@PathVariable long id){
        return accommodationService.findById(id);
    }

    @DeleteMapping("/{id}")
    @Authenticated(Role.HOST)
    public void deleteAccommodation(@PathVariable long id, UserEntity user){
        accommodationService.deleteById(id, user);
    }
}

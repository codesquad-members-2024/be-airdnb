package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.accommodation.dto.response.AccommodationDetailResponse;
import team07.airbnb.data.accommodation.dto.response.AccommodationListResponse;
import team07.airbnb.data.product.dto.response.SimpleProductResponse;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.user.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static team07.airbnb.data.user.enums.Role.HOST;

@Tag(name = "숙소")
@RequestMapping("/accommodation")
@RestController
@RequiredArgsConstructor
public class AccommodationInquiryController {

    private final AccommodationService accommodationService;
    private final UserService userService;

    @Tag(name = "User")
    @Operation(summary = "모든 숙소 조회", description = "스쿼드비엔비에 등록된 모든 숙소를 조회합니다.")
    @GetMapping
    @ResponseStatus(OK)
    public List<AccommodationListResponse> findAll() {
        return previewOf(accommodationService.findAllAccommodations());
    }

    @Tag(name = "User")
    @Operation(summary = "주변 숙소 조회", description = "지정한 위치로부터 지정한 반경 내의 숙소를 조회합니다.")
    @GetMapping("/location")
    @ResponseStatus(OK)
    public List<AccommodationListResponse> findNeighbor(
            @RequestParam double longitude,
            @RequestParam double latitude,
            @RequestParam double distance) {

        return previewOf(accommodationService.findNearbyAccommodations(longitude, latitude, distance * 1000));
    }

    @Tag(name = "Host")
    @Operation(summary = "나의 숙소 조회", description = "내가 등록한 숙소를 조회합니다.")
    @Authenticated(HOST)
    @GetMapping("/my")
    public List<AccommodationListResponse> myAccommodations(
            @Parameter(hidden = true)UserEntity user) {
        return previewOf(accommodationService.findByHost(user));
    }

    @Tag(name = "User")
    @Operation(summary = "숙소 상세 조회", description = "숙소의 상세 정보를 조회합니다.")
    @GetMapping("/{accommodationId}")
    @ResponseStatus(OK)
    public AccommodationDetailResponse accommodationDetail(@PathVariable long accommodationId) {
        return AccommodationDetailResponse.of(accommodationService.findById(accommodationId));
    }

    @Tag(name = "User")
    @Operation(summary = "예약 가능 일자 조회", description = "지정 년월 중 숙소의 예약 가능 일자와 가격을 조회합니다.")
    @GetMapping("/available/{accommodationId}/{date}")
    @ResponseStatus(OK)
    public List<SimpleProductResponse> availableProducts(@PathVariable LocalDate date,
                                                         @PathVariable Long accommodationId) {
        return accommodationService.findAvailableProductsInMonth(date, accommodationId)
                .stream()
                .map(SimpleProductResponse::of)
                .toList();
    }

    private List<AccommodationListResponse> previewOf(List<AccommodationEntity> accommodations) {
        return accommodations.stream().map(AccommodationListResponse::of).toList();
    }
}

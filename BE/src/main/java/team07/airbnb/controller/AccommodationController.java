package team07.airbnb.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import team07.airbnb.common.auth.aop.Authenticated;
import team07.airbnb.data.accommodation.dto.request.AccommodationCreateRequest;
import team07.airbnb.data.accommodation.dto.request.AccommodationDescriptionRequest;
import team07.airbnb.data.accommodation.dto.request.BaseAccommodationInfoRequest;
import team07.airbnb.data.accommodation.dto.response.AccommodationDetailResponse;
import team07.airbnb.data.accommodation.dto.response.AccommodationListResponse;
import team07.airbnb.data.product.dto.response.SimpleProductResponse;
import team07.airbnb.data.user.dto.response.TokenUserInfo;
import team07.airbnb.data.user.enums.Role;
import team07.airbnb.entity.AccommodationEntity;
import team07.airbnb.entity.UserEntity;
import team07.airbnb.entity.embed.RoomInformation;
import team07.airbnb.service.accommodation.AccommodationService;
import team07.airbnb.service.user.UserService;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;
import static team07.airbnb.data.user.enums.Role.HOST;
import static team07.airbnb.data.user.enums.Role.USER;

@Tag(name = "숙소")
@RequestMapping("/accommodation")
@RestController
@RequiredArgsConstructor
public class AccommodationController {
    private final AccommodationService accommodationService;

    @Tag(name = "Host")
    @Operation(summary = "숙소 등록", description = "스쿼드비엔비에 숙소를 등록합니다.")
    @PostMapping
    @Authenticated(USER)
    @ResponseStatus(OK)
    public AccommodationListResponse createAccommodation(@RequestBody AccommodationCreateRequest createRequest, UserEntity user) {
        return AccommodationListResponse.of(accommodationService.addAccommodation(
                createRequest.toEntity(user)
        ));
    }

    @Tag(name = "Host")
    @Operation(summary = "숙소의 방 정보 수정")
    @Authenticated(Role.HOST)
    @PatchMapping("{id}/roomInfo")
    @ResponseStatus(HttpStatus.OK)
    public AccommodationDetailResponse updateRoomInformation(@PathVariable Long id,
                                                             @RequestBody RoomInformation info,
                                                             UserEntity user) {
        return AccommodationDetailResponse.of(
                accommodationService.updateAccommodation(
                        id,
                        info,
                        user
                ));
    }

    @Tag(name = "Host")
    @Operation(summary = "숙소의 설명 정보 수정")
    @Authenticated(Role.HOST)
    @PatchMapping("{id}/description")
    @ResponseStatus(HttpStatus.OK)
    public AccommodationDetailResponse updateDescription(@PathVariable Long id,
                                                         @RequestBody AccommodationDescriptionRequest description,
                                                         UserEntity user) {
        return AccommodationDetailResponse.of(
                accommodationService.updateAccommodation(
                        id,
                        description.name(),
                        description.description(),
                        user
                ));
    }

    @Tag(name = "Host")
    @Operation(summary = "숙소의 기본 정보 수정")
    @Authenticated(Role.HOST)
    @PatchMapping("{id}/base")
    @ResponseStatus(HttpStatus.OK)
    public AccommodationDetailResponse updateBaseInformation(@PathVariable Long id,
                                                             @RequestBody BaseAccommodationInfoRequest baseInfo,
                                                             UserEntity user) {

        return AccommodationDetailResponse.of(accommodationService.updateAccommodation(
                id,
                baseInfo.type(),
                baseInfo.address(),
                baseInfo.basePricePerDay(),
                user
        ));
    }

    @Tag(name = "Host")
    @Operation(summary = "숙소의 사진 수정")
    @Authenticated(Role.HOST)
    @PatchMapping("{id}/picture")
    @ResponseStatus(HttpStatus.OK)
    public AccommodationDetailResponse updatePictures(@PathVariable Long id,
                                                      @RequestBody List<String> pictureUrls,
                                                      UserEntity user) {
        return AccommodationDetailResponse.of(accommodationService.updateAccommodation(
                id,
                pictureUrls,
                user
        ));
    }

    @Tag(name = "Host")
    @Operation(summary = "숙소 삭제", description = "등록한 숙소를 삭제합니다.")
    @DeleteMapping("/{id}")
    @Authenticated(HOST)
    @ResponseStatus(OK)
    public void deleteAccommodation(@PathVariable long id, UserEntity user) {
        accommodationService.deleteById(id, user);
    }
}

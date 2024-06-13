package com.airdnb.clone.domain.stay.controller;

import com.airdnb.clone.domain.stay.controller.dto.request.StayCreateRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.AliasEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.AmenitiesEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.CheckInOutTimeEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.DescriptionEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.FeeEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.ImagesEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.LocationEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.RoomInfoEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.StatusEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.request.edit.TypeEditRequest;
import com.airdnb.clone.domain.stay.controller.dto.response.StayDetailResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.AliasEditResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.AvailableAmenitiesResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.CheckInOutTimeEditResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.DescriptionEditResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.FeeEditResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.ImagesEditResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.LocationEditResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.RoomInfoResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.StatusEditResponse;
import com.airdnb.clone.domain.stay.controller.dto.response.edit.TypeEditResponse;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.service.StayService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/stays")
public class StayController {

    private final StayService stayService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StayDetailResponse create(@Valid @RequestBody StayCreateRequest request) {
        return stayService.create(request.toStayBuilder(), request.getHostId(), request.getAmenityIds());
    }

    @GetMapping("/{id}")
    public StayDetailResponse getStayDetail(@PathVariable("id") Long id) {
        return stayService.getStay(id);
    }

    @GetMapping
    public List<StayDetailResponse> getAllStays() {
        return stayService.getStays().stream()
                .map(StayDetailResponse::of)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        stayService.delete(id);
    }

    @PatchMapping("/{id}/alias")
    public AliasEditResponse editAlias(@PathVariable("id") Long id,
                                       @Valid @RequestBody AliasEditRequest request) {
        return stayService.editAlias(id, request.getAlias());
    }

    @PatchMapping("/{id}/amenities")
    public AvailableAmenitiesResponse editAmenities(@PathVariable("id") Long id,
                                                    @Valid @RequestBody AmenitiesEditRequest request) {
        return stayService.editAmenities(id, request.getAmenityIds());
    }

    @PatchMapping("/{id}/check-in-out")
    public CheckInOutTimeEditResponse editCheckInOutTime(@PathVariable("id") Long id,
                                                         @Valid @RequestBody CheckInOutTimeEditRequest request) {
        return stayService.editCheckInOutTime(id, request.getCheckInTime(), request.getCheckOutTime());
    }

    @PatchMapping("/{id}/description")
    public DescriptionEditResponse editDescription(@PathVariable("id") Long id,
                                                   @Valid @RequestBody DescriptionEditRequest request) {
        return stayService.editDescription(id, request.getDescription());
    }

    @PatchMapping("/{id}/fee")
    public FeeEditResponse editFee(@PathVariable("id") Long id,
                                   @Valid @RequestBody FeeEditRequest request) {
        return stayService.editFee(id, request.getFee());
    }

    @PatchMapping("/{id}/images")
    public ImagesEditResponse editImages(@PathVariable("id") Long id,
                                         @Valid @RequestBody ImagesEditRequest request) {
        return stayService.editImages(id, request.getImages());
    }

    @PatchMapping("/{id}/location")
    public LocationEditResponse editLocation(@PathVariable("id") Long id,
                                             @Valid @RequestBody LocationEditRequest request) {
        return stayService.editLocation(id, request.getLocation());
    }

    @PatchMapping("/{id}/room")
    public RoomInfoResponse editRoomInfo(@PathVariable("id") Long id,
                                         @Valid @RequestBody RoomInfoEditRequest request) {
        return stayService.editRoomInfo(id, request.toRoomInformation());
    }

    @PatchMapping("/{id}/status")
    public StatusEditResponse editStatus(@PathVariable("id") Long id,
                                         @Valid @RequestBody StatusEditRequest request) {
        return stayService.editStatus(id, request.getStatus());
    }

    @PatchMapping("/{id}/type")
    public TypeEditResponse editType(@PathVariable("id") Long id,
                                     @Valid @RequestBody TypeEditRequest request) {
        return stayService.editType(id, request.getType());
    }
}

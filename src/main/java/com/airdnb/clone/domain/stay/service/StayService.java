package com.airdnb.clone.domain.stay.service;

import static com.airdnb.clone.domain.stay.entity.Stay.StayBuilder;

import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.member.repository.MemberRepository;
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
import com.airdnb.clone.domain.stay.entity.AvailableAmenity;
import com.airdnb.clone.domain.stay.entity.RoomInformation;
import com.airdnb.clone.domain.stay.entity.Stay;
import com.airdnb.clone.domain.stay.entity.Stay.Status;
import com.airdnb.clone.domain.stay.entity.Stay.Type;
import com.airdnb.clone.domain.stay.entity.StayFee;
import com.airdnb.clone.domain.stay.entity.StayImage;
import com.airdnb.clone.domain.stay.repository.AmenityRepository;
import com.airdnb.clone.domain.stay.repository.AvailableAmenityRepository;
import com.airdnb.clone.domain.stay.repository.StayRepository;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StayService {

    private final StayRepository stayRepository;
    private final MemberRepository memberRepository;
    private final AvailableAmenityRepository availableAmenityRepository;
    private final AmenityRepository amenityRepository;

    @Transactional
    public StayDetailResponse create(StayBuilder builder, Long hostId, List<Long> amenityIds) {
        Stay savedStay = saveByHostId(builder, hostId);

        saveAvailableAmenities(amenityIds, savedStay);

        return StayDetailResponse.of(savedStay);
    }

    private Stay saveByHostId(StayBuilder builder, Long hostId) {
        Member host = memberRepository.findById(hostId)
                .orElseThrow();

        Stay stay = builder.host(host).build();

        return stayRepository.save(stay);
    }

    private void saveAvailableAmenities(List<Long> amenityIds, Stay savedStay) {
        amenityIds.stream()
                .map(amenityId -> amenityRepository.findById(amenityId).orElseThrow())
                .forEach(amenity -> {
                    AvailableAmenity availableAmenity = AvailableAmenity.builder() // 이용 가능 비품 생성
                            .stay(savedStay)
                            .amenity(amenity)
                            .build();

                    availableAmenity.setStay(savedStay); // 양방향 연관관계 연결

                    availableAmenityRepository.save(availableAmenity); // 이용 가능 비품 등록
                });
    }

    public StayDetailResponse getStay(Long stayId) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow();

        return StayDetailResponse.of(stay);
    }

    @Transactional(readOnly = true)
    public List<Stay> getStays() {
        return stayRepository.findAll();
    }

    @Transactional
    public AliasEditResponse editAlias(Long stayId, String alias) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeAlias(alias);

        return AliasEditResponse.of(stay.getAlias());
    }

    @Transactional
    public LocationEditResponse editLocation(Long stayId, String location) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeLocation(location);

        return LocationEditResponse.of(stay.getLocation());
    }

    @Transactional
    public CheckInOutTimeEditResponse editCheckInOutTime(Long stayId, LocalTime checkInTime, LocalTime checkOutTime) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeCheckInOutTime(checkInTime, checkOutTime);

        return CheckInOutTimeEditResponse.of(stay.getCheckInTime(), stay.getCheckOutTime());
    }

    @Transactional
    public DescriptionEditResponse editDescription(Long stayId, String description) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeDescription(description);

        return DescriptionEditResponse.of(stay.getDescription());
    }

    @Transactional
    public FeeEditResponse editFee(Long stayId, StayFee fee) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeFee(fee);

        return FeeEditResponse.of(stay.getFee());
    }

    @Transactional
    public TypeEditResponse editType(Long stayId, Type type) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeType(type);

        return TypeEditResponse.of(stay.getType());
    }

    @Transactional
    public StatusEditResponse editStatus(Long stayId, Status status) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeStatus(status);

        return StatusEditResponse.of(stay.getStatus());
    }

    @Transactional
    public AvailableAmenitiesResponse editAmenities(Long stayId, List<Long> amenityIds) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow();

        stay.getAmenities().stream()
                .filter(availableAmenity -> !amenityIds.contains(availableAmenity.getAmenity().getId()))
                .forEach(AvailableAmenity::deleteFromStay);

        return AvailableAmenitiesResponse.of(stay.getAmenities());
    }

    @Transactional
    public ImagesEditResponse editImages(Long stayId, List<StayImage> images) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeImages(images);

        return ImagesEditResponse.of(stay.getImages());
    }

    @Transactional
    public RoomInfoResponse editRoomInfo(Long stayId, RoomInformation roomInfo) {
        Stay stay = stayRepository.findById(stayId)
                .orElseThrow()
                .changeRoomInfo(roomInfo);

        return RoomInfoResponse.of(stay.getRoomInfo());
    }

    @Transactional
    public void delete(Long stayId) {
        stayRepository.deleteById(stayId);
    }
}

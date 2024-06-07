package com.airdnb.clone.domain.stay.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Stay {

    private Long id;
    private Long hostId;
    private String alias;
    private String location;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String description;
    private StayFee fee;
    private StayType type;
    private StayStatus status;
    private List<Amenity> amenities;
    private List<StayImage> images;
    private RoomInformation roomInfo;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void changeStayStatus(StayStatus status) {
        this.status = status;
    }

    public enum StayStatus {
        OPEN, REPAIR, CLOSE
    }

    public enum StayType {
        APARTMENT, ONE_ROOM, TWO_ROOM, HOSTEL, GUEST_HOUSE, COUNTRY_SIDE
    }
}
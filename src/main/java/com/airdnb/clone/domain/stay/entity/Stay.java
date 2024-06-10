package com.airdnb.clone.domain.stay.entity;

import com.airdnb.clone.domain.common.BaseTimeEntity;
import com.airdnb.clone.domain.member.Member;
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
public class Stay extends BaseTimeEntity {

    private Long id;
    private Member hostId;
    private String alias;
    private String location;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private String description;
    private StayFee fee;
    private Type type;
    private Status status;
    private List<Amenity> amenities;
    private List<StayImage> images;
    private RoomInformation roomInfo;

    public void changeAlias(String alias) {
        this.alias = alias;
    }

    public void changeLocation(String location) {
        this.location = location;
    }

    public void changeCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public void changeCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }

    public void changeDescription(String description) {
        this.description = description;
    }

    public void changeType(Type type) {
        this.type = type;
    }

    public void changeStatus(Status status) {
        this.status = status;
    }

    public void changeAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public void changeImages(List<StayImage> images) {
        this.images = images;
    }

    public void changeRoomInfo(RoomInformation roomInfo) {
        this.roomInfo = roomInfo;
    }

    public enum Status {
        OPEN, REPAIR, CLOSE
    }

    public enum Type {
        APARTMENT, ONE_ROOM, TWO_ROOM, HOSTEL, GUEST_HOUSE, COUNTRY_SIDE
    }
}
package com.airdnb.clone.domain.stay.entity;

import com.airdnb.clone.domain.common.BaseTimeEntity;
import com.airdnb.clone.domain.member.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.ArrayList;
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
@Table(name = "STAY")
@Entity
public class Stay extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "HOST_ID", foreignKey = @ForeignKey(name = "FK_MEMBER_STAY_ID"))
    private Member host;

    @Column(name = "ALIAS")
    private String alias;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "CHECK_IN_TIME")
    private LocalTime checkInTime;

    @Column(name = "CHECK_OUT_TIME")
    private LocalTime checkOutTime;

    @Column(name = "DESCRIPTION")
    private String description;

    @Embedded
    private StayFee fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @Builder.Default
    @OneToMany(mappedBy = "stay", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AvailableAmenity> amenities = new ArrayList<>();

    @Builder.Default
    @ElementCollection
    @CollectionTable(name = "STAY_IMAGE", joinColumns = {
            @JoinColumn(name = "STAY_ID", foreignKey = @ForeignKey(name = "FK_STAY_IMAGE_ID")) // 중간 테이블이 가질 숙소 ID
    })
    private List<StayImage> images = new ArrayList<>();

    @Embedded
    private RoomInformation roomInfo;

    public Stay changeAlias(String alias) {
        this.alias = alias;

        return this;
    }

    public Stay changeLocation(String location) {
        this.location = location;

        return this;
    }

    public Stay changeCheckInOutTime(LocalTime checkInTime, LocalTime checkOutTime) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;

        return this;
    }

    public Stay changeDescription(String description) {
        this.description = description;

        return this;
    }

    public Stay changeFee(StayFee fee) {
        this.fee = fee;

        return this;
    }

    public Stay changeType(Type type) {
        this.type = type;

        return this;
    }

    public Stay changeStatus(Status status) {
        this.status = status;

        return this;
    }

    public Stay changeImages(List<StayImage> images) {
        this.images = images;

        return this;
    }

    public Stay changeRoomInfo(RoomInformation roomInfo) {
        this.roomInfo = roomInfo;

        return this;
    }

    public enum Status {
        OPEN, REPAIR, CLOSED
    }

    public enum Type {
        APARTMENT, ONE_ROOM, TWO_ROOM, HOSTEL, GUEST_HOUSE, COUNTRY_SIDE
    }
}
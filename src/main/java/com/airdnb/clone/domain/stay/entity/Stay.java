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
import java.util.List;
import java.util.Set;
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
    @Column(name = "FEE")
    private StayFee fee;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private Type type;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status;

    @OneToMany(mappedBy = "stay", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AvailableAmenity> amenities;

    @ElementCollection
    @CollectionTable(name = "STAY_IMAGE", joinColumns = {
            @JoinColumn(name = "STAY_ID", foreignKey = @ForeignKey(name = "FK_STAY_IMAGE_ID")) // 중간 테이블이 가질 숙소 ID
    })
    private List<StayImage> images;

    @Embedded
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
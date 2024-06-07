package com.airdnb.stay.entity;

import com.airdnb.global.NotFoundException;
import com.airdnb.image.entity.Image;
import com.airdnb.member.entity.Member;
import com.airdnb.staytag.StayTag;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Stay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Integer maxGuests;
    @Embedded
    private Location location;
    @Enumerated(EnumType.STRING)
    private StayStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "host_id")
    private Member host;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "img_id")
    private Image image;
    @Enumerated(EnumType.STRING)
    private StayType type;
    @OneToMany(mappedBy = "stay")
    private List<StayTag> stayTags = new ArrayList<>();

    @Builder
    public Stay(String name, Integer price, LocalDateTime startDate, LocalDateTime endDate, Integer maxGuests,
                Location location, StayStatus status, Member host, Image image, StayType type) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.maxGuests = maxGuests;
        this.location = location;
        this.status = status;
        this.host = host;
        this.image = image;
        this.type = type;
    }

    public void softDelete() {
        this.status = StayStatus.DELETED;
    }

    public enum StayType {
        APT, PENSION, HOTEL;

        public static StayType of(String typeValue) {
            return Arrays.stream(values())
                    .filter(stayType -> stayType.name().equals(typeValue.toUpperCase()))
                    .findAny()
                    .orElseThrow(() -> new NotFoundException("일치하는 숙소 타입을 찾을 수 없습니다."));
        }
    }

    public enum StayStatus {
        ACTIVE, RESERVED, DELETED
    }
}

package com.airdnb.clone.domain.booking.entity;

import com.airdnb.clone.domain.common.BaseTimeEntity;
import com.airdnb.clone.domain.member.entity.Member;
import com.airdnb.clone.domain.stay.entity.Stay;
import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "BOOKING",
uniqueConstraints = {
        @UniqueConstraint(columnNames = {"STAY_ID", "CHECK_IN", "CHECK_OUT"})}
)
@Entity
public class Booking extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", foreignKey = @ForeignKey(name = "FK_MEMBER_RESERVE_ID"))
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STAY_ID", foreignKey = @ForeignKey(name = "FK_STAY_RESERVE_ID"))
    private Stay stay;

    @Column(name = "CHECK_IN")
    private LocalDateTime checkIn;

    @Column(name = "CHECK_OUT")
    private LocalDateTime checkOut;

    @Column(name = "TOTAL_RATE")
    private Long totalRate;

    @Column(name = "GUEST_COUNT")
    private Integer guestCount;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private Status status = Status.PENDING;

    public void changeGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public void confirm() {
        status = Status.RESERVED;
    }

    public void cancel() {
        status = Status.CANCELED;
    }

    public enum Status {
        PENDING, RESERVED, CANCELED
    }
}

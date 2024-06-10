package com.airdnb.clone.domain.reservation.entity;

import com.airdnb.clone.domain.common.BaseTimeEntity;
import com.airdnb.clone.domain.common.Guest;
import com.airdnb.clone.domain.member.Member;
import com.airdnb.clone.domain.stay.entity.Stay;
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
public class Reservation extends BaseTimeEntity {

    private Long id;
    private Member member;
    private Stay stay;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private Long totalRate;
    private Guest guest;

    @Builder.Default
    private Status status = Status.PENDING;

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

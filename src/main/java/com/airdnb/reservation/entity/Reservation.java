package com.airdnb.reservation.entity;

import com.airdnb.member.entity.Member;
import com.airdnb.stay.entity.Stay;
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
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stay_id")
    private Stay stay;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Member customer;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    @Embedded
    private ReservationPeriod reservationPeriod;
    private LocalDateTime createdAt;
    private Integer guestCount;
    private Double paymentAmount;

    @Builder
    public Reservation(Stay stay, Member customer, ReservationPeriod reservationPeriod, Integer guestCount,
                       Double paymentAmount) {
        this.stay = stay;
        this.customer = customer;
        this.status = ReservationStatus.PENDING;
        this.reservationPeriod = reservationPeriod;
        this.createdAt = LocalDateTime.now();
        this.guestCount = guestCount;
        this.paymentAmount = paymentAmount;
    }

    public void approveReservation() {
        this.status = ReservationStatus.APPROVED;
    }

    public void rejectReservation() {
        this.status = ReservationStatus.REJECTED;
    }

    public void cancelReservation() {
        this.status = ReservationStatus.CANCELED;
    }

    public enum ReservationStatus {
        PENDING, APPROVED, REJECTED, CANCELED
    }
}

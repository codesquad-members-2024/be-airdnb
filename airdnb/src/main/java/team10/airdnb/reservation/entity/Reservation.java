package team10.airdnb.reservation.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.member.entity.Member;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "UPDATE reservation SET deleted = true, is_confirmed = true WHERE id = ?")

public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accommodation_id", nullable = false)
    private Accommodation accommodation;

    @Column(name = "check_in_date")
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    private LocalDate checkOutDate;

    @Column(name = "capacity")
    private Integer capacity;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "deleted")
    private boolean deleted;

    public void updateConfirmStatus(boolean status) {
        this.isConfirmed = status;
    }
}

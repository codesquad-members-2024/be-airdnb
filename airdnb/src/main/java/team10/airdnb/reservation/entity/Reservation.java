package team10.airdnb.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team10.airdnb.accommodation.entity.Accommodation;
import team10.airdnb.accommodation.entity.embedded.AccommodationFee;
import team10.airdnb.member.entity.Member;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "reservation")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
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

    // 숙박 총 비용을 계산
    public static BigDecimal calculateTotalPrice(AccommodationFee accommodationfee, LocalDate checkInDate, LocalDate checkOutDate) {
        // 두 날짜 사이의 일 수를 계산
        BigDecimal numberOfDays = BigDecimal.valueOf(ChronoUnit.DAYS.between(checkInDate, checkOutDate));

        BigDecimal dayRate = accommodationfee.getDayRate();

        BigDecimal cleaningFee = accommodationfee.getCleaningFee();

        // 요금 계산
        BigDecimal totalDayRate = dayRate.multiply(numberOfDays);

        BigDecimal totalPrice = totalDayRate.add(cleaningFee);

        return totalPrice;
    }
}

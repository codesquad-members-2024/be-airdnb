package team07.airbnb.domain.payment;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Getter
@Embeddable
public class Fee {
    private int bookingFee;
    private int serviceFee;
    private int cancellationFee;
    private int taxes;
    private int totalFee = bookingFee + serviceFee + cancellationFee + taxes;
}

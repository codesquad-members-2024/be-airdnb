package codesquad.team05.domain.coupon.kafka;

import codesquad.team05.web.coupon.dto.request.CouponMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void create(CouponMessage coupon) {

        CompletableFuture<SendResult<String, Object>> message =
                kafkaTemplate.send("coupon_create", coupon);

        message.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.err.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}

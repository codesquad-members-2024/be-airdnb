package team07.airbnb.domain.discount;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import team07.airbnb.domain.discount.beans.DiscountPolicy;
import team07.airbnb.entity.DiscountPolicyEntity;
import team07.airbnb.domain.discount.exception.DiscountPolicyNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountPolicyService {

    private final DiscountPolicyRepository discountPolicyRepository;
    private final ApplicationContext ac;

    public long getDiscountPrice(long roughPrice) {
        //일단 주단위 할인
        Optional<DiscountPolicyEntity> weekDiscount = discountPolicyRepository.findByDescription("주단위할인");
        DiscountPolicyEntity entity = weekDiscount.orElseThrow(DiscountPolicyNotFoundException::new);

        String policyBeanName = entity.getPolicyBeanName();
        DiscountPolicy bean = (DiscountPolicy) ac.getBean(policyBeanName);

        return bean.getDiscountPrice(roughPrice);
    }
}

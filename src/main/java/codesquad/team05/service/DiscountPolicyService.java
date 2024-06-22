package codesquad.team05.service;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.AccommodationBatchRepository;
import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.discount.DiscountPolicy;
import codesquad.team05.domain.discount.DiscountPolicyRepository;
import codesquad.team05.web.accommodation.dto.request.DiscountForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class DiscountPolicyService {

    private final AccommodationRepository accommodationRepository;
    private final AccommodationBatchRepository batchRepository;
    private final DiscountPolicyRepository discountPolicyRepository;

    public void setDiscountPolicy(Long accommodationId, DiscountForm discountForm) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow();

        DiscountPolicy discountPolicy =
                new DiscountPolicy(discountForm.getDiscountRate()
                        , discountForm.getStartDate()
                        , discountForm.getEndDate()
                        , accommodationId);

        if (discountPolicy.isStartDate()) {
            accommodation.startDiscount();
        }

        discountPolicyRepository.save(discountPolicy);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void checkAndUpdateDiscounts() {

        /*
         매일 자정에 모든 숙소 할인 정책들을 조회해서 할인 시작일과 종료일은 체크한다.

         ex) 오늘 날짜 6월 21일

         (1) 숙소 1 시작일 6월 21일, 종료일 6월 27일
         (2) 숙소2 2 시작일 6월 15일, 종료일 6월 21일

         (1)번 숙소1은 오늘부터 할인 시작, (2)번 숙소2는 오늘부터 할인 종료
         */

        List<Long> startDiscountIds = discountPolicyRepository
                .findByStartDate(today())
                .stream()
                .map(DiscountPolicy::getAccommodationId)
                .toList();


        List<Long> endDiscountIds = discountPolicyRepository
                .findByEndDate(yesterday())
                .stream()
                .map(DiscountPolicy::getAccommodationId)
                .toList();

        if (!startDiscountIds.isEmpty()) {
            batchRepository.startDiscount(startDiscountIds);
        }

        if (!endDiscountIds.isEmpty()) {
            batchRepository.endDiscount(endDiscountIds);
            discountPolicyRepository.deleteByEndDate(yesterday());
        }

    }

    private LocalDate today() {
        return LocalDate.now();
    }

    private LocalDate yesterday() {
        return LocalDate.now().minusDays(1);
    }

}

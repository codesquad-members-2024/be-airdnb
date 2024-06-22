package codesquad.team05.service;

import codesquad.team05.domain.accommodation.Accommodation;
import codesquad.team05.domain.accommodation.AccommodationRepository;
import codesquad.team05.domain.servicecharge.ServiceCharge;
import codesquad.team05.domain.servicecharge.ServiceChargeRepository;
import codesquad.team05.web.accommodation.dto.request.ServiceChargeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ServiceChargeService {

    private final ServiceChargeRepository serviceChargeRepository;
    private final AccommodationRepository accommodationRepository;

    public Long setServiceCharge(Long accommodationId, ServiceChargeDto serviceChargeDto) {

        Accommodation accommodation = accommodationRepository.findById(accommodationId).orElseThrow();
        ServiceCharge serviceCharge
                = new ServiceCharge(serviceChargeDto.getServiceType()
                , serviceChargeDto.getFee()
                , serviceChargeDto.getIntervalDays());

        ServiceCharge save = serviceChargeRepository.save(serviceCharge);
        accommodation.addServiceCharge(serviceCharge);

        return save.getId();
    }

    public ServiceCharge getServiceCharge(Long serviceChargeId) {

        return serviceChargeRepository.findById(serviceChargeId)
                .orElseThrow();

    }

    public void delete(Long serviceChargeId) {

        serviceChargeRepository.deleteById(serviceChargeId);
    }

}

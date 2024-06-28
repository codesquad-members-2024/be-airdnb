package codesquad.team05.domain.accommodation;

import codesquad.team05.domain.servicecharge.ServiceCharge;
import codesquad.team05.domain.servicecharge.ServiceChargeRepository;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.service.AccommodationService;
import codesquad.team05.service.ServiceChargeService;
import codesquad.team05.util.AccommodationMapper;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.AccommodationUpdateServiceRequest;
import codesquad.team05.web.accommodation.dto.request.ServiceChargeDto;
import codesquad.team05.web.accommodation.dto.response.AccommodationResponse;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;

import static codesquad.team05.domain.accommodation.AccommodationType.HOTEL;
import static codesquad.team05.domain.servicecharge.ServiceType.CLEANING;

@Transactional
@SpringBootTest
class AccommodationTest {

    private final AccommodationService accommodationService;
    private final UserRepository userRepository;
    private final ServiceChargeService serviceChargeService;
    private final ServiceChargeRepository serviceChargeRepository;

    @Autowired
    public AccommodationTest(
            AccommodationService accommodationService,
            UserRepository userRepository,
            ServiceChargeService serviceChargeService,
            ServiceChargeRepository serviceChargeRepository
    ) {
        this.accommodationService = accommodationService;
        this.userRepository = userRepository;
        this.serviceChargeService = serviceChargeService;
        this.serviceChargeRepository = serviceChargeRepository;
    }

    @DisplayName("호스트가 숙소를 등록한다.")
    @Test
    public void test() {
        User user = new User("fnelclsrn", "정연호", "123", "소사1", LocalDate.now());
        userRepository.save(user);
        user.enrollHost();
        AccommodationSave accommodationSave = new AccommodationSave(
                "숙소1",
                1000,
                "소사1",
                1,
                2,
                3,
                "집 좋음",
                "없음",
                HOTEL,
                new ArrayList<>()
        );
        Long id = accommodationService.register(AccommodationMapper.toSaveService(accommodationSave, new ArrayList<>()));
        AccommodationResponse accommodationResponse = accommodationService.getAccommodationById(id);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(accommodationResponse.getName()).isEqualTo("숙소1");
        softAssertions.assertThat(accommodationResponse.getPrice()).isEqualTo(1000);
        softAssertions.assertThat(accommodationResponse.getAddress()).isEqualTo("소사1");
        softAssertions.assertThat(accommodationResponse.getMaxCapacity()).isEqualTo(1);
        softAssertions.assertThat(accommodationResponse.getRoomCount()).isEqualTo(2);
        softAssertions.assertThat(accommodationResponse.getBedCount()).isEqualTo(3);
        softAssertions.assertThat(accommodationResponse.getDescription()).isEqualTo("집 좋음");
    }


    @DisplayName("호스트가 숙소에 서비스 비용을 등록한다.")
    @Test
    public void test2() {
        User user = new User("fnelclsrn", "정연호", "123", "소사1", LocalDate.now());
        userRepository.save(user);
        user.enrollHost();
        AccommodationSave accommodationSave = new AccommodationSave(
                "숙소1",
                1000,
                "소사1",
                1,
                2,
                3,
                "집 좋음",
                "없음",
                HOTEL,
                new ArrayList<>()
        );

        Long id = accommodationService.register(AccommodationMapper.toSaveService(accommodationSave, new ArrayList<>()));
        ServiceChargeDto serviceChargeDto = new ServiceChargeDto(CLEANING, 2000, 3);
        Long id2 = serviceChargeService.setServiceCharge(id, serviceChargeDto);
        ServiceCharge serviceCharge = serviceChargeService.getServiceCharge(id2);
        SoftAssertions softAssertions = new SoftAssertions();

        softAssertions.assertThat(serviceCharge.getServiceType()).isEqualTo(CLEANING);
        softAssertions.assertThat(serviceCharge.getFee()).isEqualTo(1000);
        softAssertions.assertThat(serviceCharge.getIntervalDays()).isEqualTo(3);
    }

    @DisplayName("호스트가 숙소의 서비스 비용을 삭제한다.")
    @Test
    public void test3() {
        User user = new User("fnelclsrn", "정연호", "123", "소사1", LocalDate.now());
        userRepository.save(user);
        user.enrollHost();
        AccommodationSave accommodationSave = new AccommodationSave(
                "숙소1",
                1000,
                "소사1",
                1,
                2,
                3,
                "집 좋음",
                "없음",
                HOTEL,
                new ArrayList<>()
        );

        Long id = accommodationService.register(AccommodationMapper.toSaveService(accommodationSave, new ArrayList<>()));
        ServiceChargeDto serviceChargeDto = new ServiceChargeDto(CLEANING, 2000, 3);
        Long id2 = serviceChargeService.setServiceCharge(id, serviceChargeDto);

        serviceChargeService.delete(id2);

        AccommodationUpdateServiceRequest accommodationUpdateServiceRequest = new AccommodationUpdateServiceRequest(
                "숙소1",
                1000,
                "소사1",
                1,
                2,
                3,
                "집 좋음",
                "변경함",
                new ArrayList<>(),
                HOTEL,
                new ArrayList<>()
        );

        accommodationService.updateAccommodation(id, accommodationUpdateServiceRequest);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(serviceChargeRepository.existsById(id2)).isFalse();
    }
}
package codesquad.team05.domain.accomodation;

import codesquad.team05.domain.servicecharge.ServiceCharge;
import codesquad.team05.domain.servicecharge.ServiceChargeRepository;
import codesquad.team05.domain.user.User;
import codesquad.team05.domain.user.UserRepository;
import codesquad.team05.service.AccommodationService;
import codesquad.team05.service.ServiceChargeService;
import codesquad.team05.web.accommodation.dto.request.AccommodationSave;
import codesquad.team05.web.accommodation.dto.request.ServiceChargeDto;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static codesquad.team05.domain.accomodation.AccommodationType.HOTEL;
import static codesquad.team05.domain.servicecharge.ServiceType.CLEANING;

@Transactional
@SpringBootTest
class AccommodationTest {

    private final EntityManager entityManager;
    private final AccommodationService accommodationService;
    private final UserRepository userRepository;
    private final ServiceChargeService serviceChargeService;
    private final ServiceChargeRepository serviceChargeRepository;

    @Autowired
    public AccommodationTest(EntityManager entityManager, AccommodationService accommodationService, UserRepository userRepository, ServiceChargeService serviceChargeService, ServiceChargeRepository serviceChargeRepository) {
        this.entityManager = entityManager;
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


        AccommodationSave accommodationSave = new AccommodationSave("숙소1"
                , 1000
                , "소사1"
                , 1
                , 2, 3, "집 좋음",
                "없음", 1L, null, HOTEL);

        Long id = accommodationService.register(accommodationSave, null);

        Accommodation accommodation = accommodationService.getAccommodation(id);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(accommodation.getName()).isEqualTo("숙소1");
        softAssertions.assertThat(accommodation.getPrice()).isEqualTo(1000);
        softAssertions.assertThat(accommodation.getAddress()).isEqualTo("소사1");
        softAssertions.assertThat(accommodation.getMaxCapacity()).isEqualTo(1);
        softAssertions.assertThat(accommodation.getRoomCount()).isEqualTo(2);
        softAssertions.assertThat(accommodation.getBedCount()).isEqualTo(3);
        softAssertions.assertThat(accommodation.getDescription()).isEqualTo("집 좋음");
    }


    @DisplayName("호스트가 숙소에 서비스 비용을 등록한다.")
    @Test
    public void test2() {

        User user = new User("fnelclsrn", "정연호", "123", "소사1", LocalDate.now());
        userRepository.save(user);

        user.enrollHost();


        AccommodationSave accommodationSave = new AccommodationSave("숙소1", 1000, "소사1", 1, 2, 3, "집 좋음",
                "없음", 1L, null, HOTEL);

        Long id = accommodationService.register(accommodationSave, null);

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

        AccommodationSave accommodationSave = new AccommodationSave("숙소1", 1000, "소사1", 1, 2, 3, "집 좋음",
                "없음", 1L, null, HOTEL);

        Long id = accommodationService.register(accommodationSave, null);

        ServiceChargeDto serviceChargeDto = new ServiceChargeDto(CLEANING, 2000, 3);
        Long id2 = serviceChargeService.setServiceCharge(id, serviceChargeDto);

        serviceChargeService.delete(id2);

        Accommodation accommodation = accommodationService.getAccommodation(id);
        accommodation.setAmenity("변경함");


        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(serviceChargeRepository.existsById(id2)).isFalse();
    }

}